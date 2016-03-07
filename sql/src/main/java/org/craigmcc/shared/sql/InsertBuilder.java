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
import org.craigmcc.shared.entities.Base;

import javax.annotation.Nonnull;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class InsertBuilder extends AbstractMutableBuilder<InsertBuilder> {

    private static final String[] KEYS = new String[] { Base.FIELD_ID };

    public InsertBuilder(@Nonnull String table) {
        this.table = table;
    }

    private final String table;

    @Override
    public PreparedStatement build(Connection connection) throws SQLException {

        List<Object> params = Lists.newArrayList();
        StringBuilder sb = new StringBuilder("INSERT INTO ")
                .append(table)
                .append(" (");
        boolean first = true;
        for (Pair pair : pairs) {
            if (first) {
                first = false;
            } else {
                sb.append(", ");
            }
            sb.append(pair.column);
        }

        sb.append(") VALUES (");
        first = true;
        for (Pair pair : pairs) {
            if (first) {
                first = false;
            } else {
                sb.append(", ");
            }
            if (Utils.literal(pair.value)) {
                sb.append(pair.value);
            } else {
                sb.append("?");
                params.add(pair.value);
            }
        }
        sb.append(")");

        String sql = sb.toString();
        PreparedStatement stmt = connection.prepareStatement(sql, KEYS);
        Utils.apply(stmt, params);
        return stmt;

    }

}
