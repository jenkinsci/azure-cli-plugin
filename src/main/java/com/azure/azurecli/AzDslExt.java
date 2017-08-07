/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.azure.azurecli;

import hudson.Extension;
import javaposse.jobdsl.dsl.helpers.step.StepContext;
import javaposse.jobdsl.plugin.ContextExtensionPoint;
import javaposse.jobdsl.plugin.DslExtensionMethod;

import java.util.List;


@Extension(optional = true)
public class AzDslExt extends ContextExtensionPoint {

    @DslExtensionMethod(context = StepContext.class)
    public Object azCommands(String servicePrincipalId, List<String> commands) {
        return new AzureCLIBuilder(servicePrincipalId, commands, true);
    }
}
