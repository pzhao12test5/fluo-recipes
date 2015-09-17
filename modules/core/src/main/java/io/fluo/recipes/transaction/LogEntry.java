/*
 * Copyright 2015 Fluo authors (see AUTHORS)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package io.fluo.recipes.transaction;

import com.google.common.base.Preconditions;
import io.fluo.api.data.Bytes;
import io.fluo.api.data.Column;

/**
 * Logs an operation (i.e GET, SET, or DELETE) in a Transaction. Multiple LogEntry objects make up a
 * {@link TxLog}.
 */
public class LogEntry {

  public enum Operation {
    GET, SET, DELETE
  }

  private Operation op;
  private Bytes row;
  private Column col;
  private Bytes value;

  private LogEntry() {}

  private LogEntry(Operation op, Bytes row, Column col, Bytes value) {
    Preconditions.checkNotNull(op);
    Preconditions.checkNotNull(row);
    Preconditions.checkNotNull(col);
    Preconditions.checkNotNull(value);
    this.op = op;
    this.row = row;
    this.col = col;
    this.value = value;
  }

  public Operation getOp() {
    return op;
  }

  public Bytes getRow() {
    return row;
  }

  public Column getColumn() {
    return col;
  }

  public Bytes getValue() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof LogEntry) {
      LogEntry other = (LogEntry) o;
      return ((op == other.op) && row.equals(other.row) && col.equals(other.col) && value
          .equals(other.value));
    }
    return false;
  }

  @Override
  public String toString() {
    return "LogEntry{op=" + op + ", row=" + row + ", col=" + col + ", value=" + value + "}";
  }

  public static LogEntry newGet(Bytes row, Column col, Bytes value) {
    return new LogEntry(Operation.GET, row, col, value);
  }

  public static LogEntry newSet(Bytes row, Column col, Bytes value) {
    return new LogEntry(Operation.SET, row, col, value);
  }

  public static LogEntry newDelete(Bytes row, Column col) {
    return new LogEntry(Operation.DELETE, row, col, Bytes.EMPTY);
  }
}