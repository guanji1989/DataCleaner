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
package org.datacleaner.monitor.server.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.security.RolesAllowed;

import org.datacleaner.monitor.configuration.TenantContext;
import org.datacleaner.monitor.configuration.TenantContextFactory;
import org.datacleaner.monitor.shared.model.SecurityRoles;
import org.datacleaner.repository.RepositoryFile;
import org.datacleaner.repository.RepositoryFolder;
import org.datacleaner.util.FileFilters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/{tenant}/results")
public class ResultsFolderController {

    @Autowired
    TenantContextFactory _tenantContextFactory;

    @RolesAllowed(SecurityRoles.VIEWER)
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<Map<String, String>> resultsFolderJson(@PathVariable("tenant") final String tenant) {
        final TenantContext context = _tenantContextFactory.getContext(tenant);

        final RepositoryFolder resultsFolder = context.getResultFolder();

        final List<Map<String, String>> result = new ArrayList<>();

        {
            final List<RepositoryFile> files =
                    resultsFolder.getFiles(null, FileFilters.ANALYSIS_RESULT_SER.getExtension());
            for (final RepositoryFile file : files) {
                final Map<String, String> map = new HashMap<>();
                map.put("filename", file.getName());
                map.put("repository_path", file.getQualifiedPath());
                result.add(map);
            }
        }

        return result;
    }
}
