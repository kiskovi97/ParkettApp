package hu.bme.sch.parkett.parkettapplication.mock

import java.util.concurrent.Executor

class MockExecutor : Executor {
    override fun execute(command: Runnable) {
        command.run()
    }
}