/**
 * DataCleaner (community edition)
 * Copyright (C) 2014 Neopost - Customer Information Management
 *
 * This copyrighted material is made available to anyone wishing to use, modify,
 * copy, or redistribute it subject to the terms and conditions of the GNU
 * Lesser General Public License, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution; if not, write to:
 * Free Software Foundation, Inc.
 * 51 Franklin Street, Fifth Floor
 * Boston, MA  02110-1301  USA
 */
package org.datacleaner.monitor.scheduling;

import java.util.List;
import java.util.Map;

import javax.annotation.security.RolesAllowed;

import org.datacleaner.monitor.scheduling.model.ExecutionIdentifier;
import org.datacleaner.monitor.scheduling.model.ExecutionLog;
import org.datacleaner.monitor.scheduling.model.ScheduleDefinition;
import org.datacleaner.monitor.shared.model.DCSecurityException;
import org.datacleaner.monitor.shared.model.JobIdentifier;
import org.datacleaner.monitor.shared.model.SecurityRoles;
import org.datacleaner.monitor.shared.model.TenantIdentifier;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Async variant of {@link SchedulingService}.
 */
public interface SchedulingServiceAsync {

    void getSchedules(TenantIdentifier tenant, boolean loadProperties,
            AsyncCallback<List<ScheduleDefinition>> callback);

    /**
     * Gets all schedules for the jobs in the jobs argument and provides access through the callback argument.
     * 
     * @param tenant tenant that contains the jobs
     * @param jobs List of jobs for which all schedules should be returned
     * @param callback callback object which provides access to the results of the method call
     */
    void getSchedules(TenantIdentifier tenant, List<JobIdentifier> jobs,
            AsyncCallback<List<ScheduleDefinition>> callback);

    /**
     * Gets all available jobs on the tenant identified by the tenant argument and provides access through the callback
     * argument.
     * 
     * @param tenant tenant that contains the jobs
     * @param callback callback object which provides access to the results of the method call
     */
    @RolesAllowed({ SecurityRoles.VIEWER, SecurityRoles.SCHEDULE_EDITOR })
    void getJobs(TenantIdentifier tenant, AsyncCallback<List<JobIdentifier>> callback) throws DCSecurityException;

    void updateSchedule(TenantIdentifier tenant, ScheduleDefinition scheduleDefinition,
            AsyncCallback<ScheduleDefinition> callback);

    void getLatestExecution(TenantIdentifier tenant, JobIdentifier job, AsyncCallback<ExecutionLog> callback);

    void cancelExecution(TenantIdentifier tenant, ExecutionLog executionLog, AsyncCallback<Boolean> callback);

    void getAllExecutions(TenantIdentifier tenant, JobIdentifier job,
            AsyncCallback<List<ExecutionIdentifier>> callback);

    void triggerExecution(TenantIdentifier tenant, JobIdentifier job, AsyncCallback<ExecutionLog> callback);

    void triggerExecution(TenantIdentifier tenant, JobIdentifier job, Map<String, String> overrideProperties,
            AsyncCallback<ExecutionLog> callback);

    void getDependentJobCandidates(TenantIdentifier tenant, ScheduleDefinition schedule,
            AsyncCallback<List<JobIdentifier>> callback);

    void getExecution(TenantIdentifier tenant, ExecutionIdentifier executionIdentifier,
            AsyncCallback<ExecutionLog> callback);

    void removeSchedule(TenantIdentifier tenant, JobIdentifier job, AsyncCallback<Void> callback);

    void getSchedule(TenantIdentifier tenant, JobIdentifier jobIdentifier, AsyncCallback<ScheduleDefinition> callback);

    void getServerDate(AsyncCallback<String> callback);

}
