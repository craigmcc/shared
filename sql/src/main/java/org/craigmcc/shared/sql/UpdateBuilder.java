/*
 * Copyright 2015 craigmcc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.craigmcc.shared.sql;

import com.google.common.collect.Lists;

import javax.annotation.Nonnull;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class UpdateBuilder extends AbstractMutableBuilder<UpdateBuilder> {

    public UpdateBuilder(@Nonnull String table) {
        this.table = table;
    }

    private final String table;
    private final WhereClauseBuilder whereClauseBuilder = new WhereClauseBuilder();

    @Override
    public PreparedStatement build(Connection connection) throws SQLException {

        if (pairs.size() < 1) {
            throw new IllegalArgumentException("At least one column+value pair must be specified");
        }
        String where = whereClauseBuilder.build();
        if ("".equals(where)) {
            throw new IllegalArgumentException("No WHERE conditions specified for this UPDATE");
        }

        List<Object> params = Lists.newArrayList();
        StringBuilder sb = new StringBuilder("UPDATE ")
                .append(table)
                .append(" SET ");
        boolean first = true;
        for (Pair pair : pairs) {
            if (first) {
                sb.append(" ");
                first = false;
            } else {
                sb.append(", ");
            }
            sb.append(pair.column);
            sb.append(" = ");
            if (Utils.literal(pair.value)) {
                sb.append(pair.value);
            } else {
                sb.append("?");
                params.add(pair.value);
            }
        }

        sb.append(where);
        params.addAll(whereClauseBuilder.params());

        String sql = sb.toString();
        PreparedStatement stmt = connection.prepareStatement(sql);
        Utils.apply(stmt, params);
        return stmt;

    }

}
