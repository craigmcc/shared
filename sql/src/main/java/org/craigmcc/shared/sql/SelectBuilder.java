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

public class SelectBuilder implements StatementBuilder {

    public static final String COUNT_COLUMN = "count(*)";

    public SelectBuilder(@Nonnull String table) {
        tables.add(table);
    }

    private final List<String> columns = Lists.newLinkedList();
    private int count = -1;
    private boolean distinct = false;
    private final List<String> groupBys = Lists.newLinkedList();
    private final List<OrderBy> orderBys = Lists.newLinkedList();
    private int startIndex = 0;
    private final List<String> tables = Lists.newLinkedList();
    private final WhereClauseBuilder whereClauseBuilder = new WhereClauseBuilder();

    @Override
    public PreparedStatement build(Connection connection) throws SQLException {
        return null; // TODO
    }

    // TODO - lots to finish here

    private static class OrderBy {

        OrderBy(@Nonnull String column, @Nonnull SqlDirection direction) {
            this.column = column;
            this.direction = direction;
        }

        final String column;
        final SqlDirection direction;

    }

}
