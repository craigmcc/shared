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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteBuilder implements StatementBuilder {

    public DeleteBuilder(@Nonnull String table) {
        this.table = table;
    }

    private final String table;
    private final WhereClauseBuilder whereClauseBuilder = new WhereClauseBuilder();

    @Override
    public PreparedStatement build(Connection connection) throws SQLException {
        String sql = toSql();
        PreparedStatement stmt = connection.prepareStatement(sql);
        Utils.apply(stmt, whereClauseBuilder.params());
        return stmt;
    }

    public DeleteBuilder or() {
        whereClauseBuilder.or();
        return this;
    }

    public DeleteBuilder or(boolean or) {
        whereClauseBuilder.or(or);
        return this;
    }

    public String toSql() {
        String where = whereClauseBuilder.build();
        if ("".equals(where)) {
            throw new IllegalArgumentException("No WHERE conditions specified for this DELETE");
        }
        StringBuilder sb = new StringBuilder("DELETE FROM ")
                .append(table)
                .append(where);
        return sb.toString();
    }

    public DeleteBuilder where(@Nonnull String column, @Nullable Object value) {
        whereClauseBuilder.where(column, value);
        return this;
    }

    public DeleteBuilder where(@Nonnull String column, @Nonnull SqlOperator operator, @Nullable Object value) {
        whereClauseBuilder.where(column, operator, value);
        return this;
    }

    public DeleteBuilder where(@Nonnull String column1, @Nullable Object value1,
                               @Nonnull String column2, @Nullable Object value2) {
        whereClauseBuilder.where(column1, value1, column2, value2);
        return this;
    }

    public DeleteBuilder where(@Nonnull String column1, @Nonnull SqlOperator operator1, @Nullable Object value1,
                               @Nonnull String column2, @Nonnull SqlOperator operator2, @Nullable Object value2) {
        whereClauseBuilder.where(column1, operator1, value1, column2, operator2, value2);
        return this;
    }

}
