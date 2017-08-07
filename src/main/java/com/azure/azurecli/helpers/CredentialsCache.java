/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.azure.azurecli.helpers;

import com.microsoft.azure.util.AzureCredentials;

public class CredentialsCache {

    public final String subscriptionId;
    public final String clientId;
    public final String clientSecret;
    public final String tenantId;

    public CredentialsCache(AzureCredentials.ServicePrincipal servicePrincipal) {
        subscriptionId = servicePrincipal.getSubscriptionId();
        clientId = servicePrincipal.getClientId();
        clientSecret = servicePrincipal.getClientSecret();
        tenantId = servicePrincipal.getTenant();

    }

}
