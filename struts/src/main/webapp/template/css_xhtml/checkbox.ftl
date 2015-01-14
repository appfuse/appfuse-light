<#--
/*
 * $Id: checkbox.ftl 720258 2008-11-24 19:05:16Z musachy $
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
-->
<#--
NOTE: The 'header' stuff that follows is in this one file for checkbox due to the fact
that for checkboxes we do not want the label field to show up as checkboxes handle their own
lables
-->
<#assign hasFieldErrors = fieldErrors?? && fieldErrors[parameters.name]??/>
<div class="checkbox<#if hasFieldErrors> has-error</#if>">
    <label>
        <#include "/${parameters.templateDir}/simple/checkbox.ftl" />
        ${parameters.label?html}
    </label>
    <#if hasFieldErrors>
    <span class="help-block">
    <#list fieldErrors[parameters.name] as error>
        ${error?html}<br/>
    </#list>
    </span><#t/>
    </#if>
</div>
