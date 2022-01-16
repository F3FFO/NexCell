/*
 * Copyright 2022 F3FFO - Federico Pierantoni
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package nexCell.controller.io;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * This class manage the auto-save.
 *
 * @author Federico Pierantoni
 */
public class AutoSave {

    /**
     * Object of {@link ScheduledExecutorService}
     */
    private ScheduledExecutorService executor;

    /**
     * Construct the {@link AutoSave#executor} to run a new thread.
     *
     * @param runnable the task to execute
     */
    public AutoSave(Runnable runnable) {
        executor = Executors.newScheduledThreadPool(1);
        executor.scheduleWithFixedDelay(runnable, 10, 15, TimeUnit.SECONDS);
    }

    /**
     * Return <code>true</code> if {@link ScheduledExecutorService#awaitTermination(long, TimeUnit) return true}; <code>false</code> otherwise.
     *
     * @return <code>true</code> if {@link ScheduledExecutorService#awaitTermination(long, TimeUnit) return true}; <code>false</code> otherwise
     */
    public boolean kill() {
        try {
            executor.shutdownNow();
            return executor.awaitTermination(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }
}
