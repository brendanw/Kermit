/*
 * Copyright (c) 2020 Touchlab
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package co.touchlab.kermit

import kotlin.test.assertEquals
import kotlin.test.assertTrue
import co.touchlab.stately.collections.frozenLinkedList

class TestLogWriter(val loggable:Severity = Severity.Verbose) : LogWriter() {
    data class LogEntry(
        val severity: Severity,
        val message: String,
        val tag: String?,
        val throwable: Throwable?
    )

    val _logs = frozenLinkedList<LogEntry>()
    val logs: List<LogEntry>
        get() = _logs.toList()

    fun assertCount(count: Int) {
        assertEquals(count, _logs.size)
    }

    fun assertLast(check: LogEntry.() -> Boolean) {
        assertTrue(_logs.last().check())
    }

    fun reset() {
        _logs.clear()
    }

    override fun isLoggable(severity: Severity): Boolean {
        return severity.ordinal >= loggable.ordinal
    }

    override fun log(severity: Severity, message: String, tag: String, throwable: Throwable?) {
        _logs.add(LogEntry(severity, message, tag, throwable))
    }
}