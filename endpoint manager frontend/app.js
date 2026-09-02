/**
 * Endpoint Management System - Application Architecture
 * Pure Vanilla JavaScript ES6 Implementation
 */

// ==========================================
// 1. MOCK DATABASE STATE (Fallback Local Database)
// ==========================================
let mockDb = {
    commands: [
        { commandId: 1, commandName: "Information Technology & Security" },
        { commandId: 2, commandName: "Operations & Fleet Management" },
        { commandId: 3, commandName: "Finance & Accounting" }
    ],
    ntns: [
        { ntnId: 1, ntnName: "Core Cyber Infrastructure", commandId: 1 },
        { ntnId: 2, ntnName: "Enterprise Software Delivery", commandId: 1 },
        { ntnId: 3, ntnName: "Logistics Support Unit", commandId: 2 },
        { ntnId: 4, ntnName: "Treasury Systems", commandId: 3 }
    ],
    agencyTypes: [
        { agencyTypeId: 1, agencyTypeName: "State Level Bureau" },
        { agencyTypeId: 2, agencyTypeName: "Federal Oversight Division" },
        { agencyTypeId: 3, agencyTypeName: "Contracted Strategic Utility" }
    ],
    agencies: [
        { agencyId: 1, agencyName: "Defense Infrastructure Agency", agencyTypeId: 2, ntnId: 1 },
        { agencyId: 2, agencyName: "Provincial Transit Services Division", agencyTypeId: 1, ntnId: 3 },
        { agencyId: 3, agencyName: "Audit Solutions & Reporting Board", agencyTypeId: 3, ntnId: 4 },
        { agencyId: 4, agencyName: "Citizen Application Support Group", agencyTypeId: 1, ntnId: 2 }
    ],
    endpoints: [
        {
            endpointId: "EP-1002",
            endpointType: "Server",
            status: "Serviceable",
            remarks: "Main routing unit located at DC-04.",
            systemOwner: "Marcus Vance",
            imRaised: "Yes",
            agencyId: 1
        },
        {
            endpointId: "EP-4412",
            endpointType: "LAPTOP",
            status: "Serviceable",
            remarks: "Front office workstation allocated for validation.",
            systemOwner: "Elena Rostova",
            imRaised: "N/A",
            agencyId: 4
        },
        {
            endpointId: "EP-5110",
            endpointType: "LAPTOP",
            status: "Unserviceable",
            remarks: "Screen unit flickering issues logged on upgrade cycle.",
            systemOwner: "Robert Diaz",
            imRaised: "No",
            agencyId: 2
        }
    ]
};

const API_BASE = "http://localhost:8080";
const LOGIN_URL = API_BASE + "/api/login";
const TOKEN_KEY = "jwtToken";
const EXPIRE_KEY = "jwtExpire";


// ==========================================
// 2. REST API INTERFACE (Isolated Call Layer)
// ==========================================
// This API object simulates typical async HTTP operations.
// These methods contain commented standard REST Fetch paradigms.



// Authentication Object
const auth = {
    getToken(){
        return localStorage.getItem(TOKEN_KEY);
    },
    isLoggedIn(){
        const token = this.getToken();
        const expiry = Number(localStorage.getItem(EXPIRE_KEY));
        if(!token)
            return false;
        if(Date.now()>expiry){
            this.logout();
            return false;
        }
        return true;
    },
    save(data){
        localStorage.setItem(TOKEN_KEY,data.token);
        localStorage.setItem(
            EXPIRE_KEY,
            Date.now()+data.expiresIn
        );
    },
    logout() {
  localStorage.removeItem(TOKEN_KEY);
  localStorage.removeItem(EXPIRE_KEY);

  resetAppState();

  document.getElementById('login-page').classList.remove('hidden');
  document.getElementById('app-container').classList.add('hidden');

  // Optional: clear visible UI leftovers
  const loginError = document.getElementById('login-error');
  if (loginError) loginError.textContent = '';
}
};

async function apiFetch(url, options = {}){
    const headers = options.headers || {};
    const token = auth.getToken();

    if(token){

        headers.Authorization = "Bearer " + token;

    }
    headers["Content-Type"] = "application/json";

    const response = await fetch(API_BASE + url,{
        ...options,
        headers
    });

    if(response.status===401){
        auth.logout();
        throw new Error("Session expired");
    }
    
    return response;
}



const api = {
    // Simulated Latency
    delay: (ms = 400) => new Promise(resolve => setTimeout(resolve, ms)),

    async getCurrentUser() {
        console.info('API CALL: GET /api/me');
        await this.delay(100);

        const response = await apiFetch('/api/me');
        if (!response.ok) throw new Error('Failed to load current user');
        return await response.json();
    },

    async getEndpoints() {
        console.info("API CALL: GET /api/endpoints");
        await this.delay();
        
        // Real Fetch Implementation:
        const response = await apiFetch('/api/endpoints');
        if (!response.ok) throw new Error('Failed to retrieve endpoints data.');
        return await response.json();
        
        
        //return [...mockDb.endpoints];
    },

    async createEndpoint(endpointData) {
        console.info("API CALL: POST /api/endpoints", endpointData);
        await this.delay();

        
        const response = await apiFetch('/api/endpoints', {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(endpointData)
        });
        if (!response.ok) {
            const text = await response.text();
    throw new Error(text);
        }
        return await response.json();
        

        //const exists = mockDb.endpoints.some(e => e.endpointId.toLowerCase() === endpointData.endpointId.toLowerCase());
        //if (exists) throw new Error(`An endpoint with ID "${endpointData.endpointId}" already exists.`);

        //mockDb.endpoints.push(endpointData);
        //return endpointData;
    },

    async updateEndpoint(id, endpointData) {
        console.info(`API CALL: PUT /api/endpoints/${id}`, endpointData);
        await this.delay();

        
        const response = await apiFetch(`/api/endpoints/${id}`, {
            method: 'PUT',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(endpointData)
        });
        if (!response.ok) throw new Error('Could not update endpoint information.');
        return await response.json();
        

        //const index = mockDb.endpoints.findIndex(e => e.endpointId === id);
        //if (index === -1) throw new Error("Target record not found.");
        
        //mockDb.endpoints[index] = { ...endpointData };
        //return mockDb.endpoints[index];
    },

    async deleteEndpoint(id) {
        console.info(`API CALL: DELETE /api/endpoints/${id}`);
        await this.delay();

        
        const response = await apiFetch(`/api/endpoints/${id}`, { method: 'DELETE' });
        if (!response.ok) throw new Error('Failed to destroy database endpoint target.');
        return true;
        

        //const index = mockDb.endpoints.findIndex(e => e.endpointId === id);
        //if (index === -1) throw new Error("Endpoint target does not exist.");
        
        //mockDb.endpoints.splice(index, 1);
        //return true;
    },

    async getCommands() {
        console.info("API CALL: GET /api/commands");
        await this.delay(100);

        const response = await apiFetch('/api/commands');
        if (!response.ok) throw new Error('Failed to retrieve commands data.');
        return await response.json();

        //return [...mockDb.commands];
    },

    async getNTNs() {
        console.info("API CALL: GET /api/ntns");
        await this.delay(100);

        const response = await apiFetch('/api/ntns');
        if (!response.ok) throw new Error('Failed to retrieve NTN data.');
        return await response.json();
        //return [...mockDb.ntns];
    },

    async getAgencies() {
        console.info("API CALL: GET /api/agencies");
        await this.delay(100);
        const response = await apiFetch('/api/agencies');
        if (!response.ok) throw new Error('Failed to retrieve agencies data.');
        return await response.json();
        //return [...mockDb.agencies];
    },

    async createAgency(agencyData) {
    console.info("API CALL: POST /api/agencies", agencyData);
    await this.delay();

    const response = await apiFetch('/api/agencies', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(agencyData)
    });

    if (!response.ok)
        throw new Error(await response.text());

    return await response.json();
},


async updateAgency(id, agencyData) {
    console.info(`API CALL: PUT /api/agencies/${id}`, agencyData);
    await this.delay();

    const response = await apiFetch(`/api/agencies/${id}`, {
        method: 'PUT',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(agencyData)
    });

    if (!response.ok)
        throw new Error(await response.text());

    return await response.json();
},


async deleteAgency(id) {
    console.info(`API CALL: DELETE /api/agencies/${id}`);

    const response = await apiFetch(`/api/agencies/${id}`, {
        method:'DELETE'
    });

    if (!response.ok)
        throw new Error(await response.text());

    return true;
},

    async getAgencyTypes() {
        console.info("API CALL: GET /api/agencyTypes");
        await this.delay();
        const response = await apiFetch('/api/agencyTypes');
        if (!response.ok) throw new Error('Failed to retrieve agencyTypes data.');
        return await response.json();
        //return [...mockDb.agencyTypes];
    },

    async createAgencyType(agencyTypeData) {
        console.info("API CALL: POST /api/agencyTypes", agencyTypeData);
        await this.delay();
        
        const response = await apiFetch('/api/agencyTypes', {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(agencyTypeData)
        });
        if (!response.ok) throw new Error('Could not record agencyType.');
        return await response.json();

        //const exists = mockDb.agencyTypes.some(at => at.typeName.toLowerCase() === agencyTypeData.typeName.toLowerCase());
        //if (exists) throw new Error(`An agency type named "${agencyTypeData.typeName}" already exists.`);

        //mockDb.agencyTypes.push(agencyTypeData);
        //return agencyTypeData;
    },

    async deleteAgencyType(id) {
        console.info(`API CALL: DELETE /api/agencyTypes/${id}`);
        await this.delay();

        const response = await apiFetch(`/api/agencyTypes/${id}`, { method: 'DELETE' });
        if (!response.ok) throw new Error('Failed to destroy database agency type target.');
        return true;

        //const associatedAgencies = mockDb.agencies.some(a => a.agencyTypeId === id);
        //if (associatedAgencies) {
        //    throw new Error("Cannot delete Agency Type because it has associated active Agencies.");
        //}

        //const index = mockDb.agencyTypes.findIndex(at => at.agencyTypeId === id);
        //if (index === -1) throw new Error("Target Agency Type does not exist.");

        //mockDb.agencyTypes.splice(index, 1);
        //return true;
    }
};

// ==========================================
// 3. APPLICATION STATE ENGINE
// ==========================================


function resetFilterUIAndState() {
  DOM.filterSearch.value = '';
  DOM.globalSearch.value = '';
  DOM.filterStatus.value = 'ALL';
  DOM.filterType.value = 'ALL';
  DOM.filterCommand.value = 'ALL';
  DOM.agfilterSearch.value='';
  DOM.agfilterCommand.value='ALL';
  DOM.agfilterNTN.value='ALL';

  state.filters = {
    search: '',
    status: 'ALL',
    type: 'ALL',
    command: 'ALL',
  };

  state.agFilters= {
        search: "",
        command: "ALL",
        ntn: "ALL"
    }
}

function resetAppState() {
  state.me = null;
  state.endpoints = [];
  state.commands = [];
  state.ntns = [];
  state.agencyTypes = [];
  state.agencies = [];
  state.currentPage = 'endpoints';
  state.filters = {
    search: '',
    status: 'ALL',
    type: 'ALL',
    command: 'ALL',
  };

  state.agFilters= {
        search: "",
        command: "ALL",
        ntn: "ALL"
    },
  state.activeDeleteCallback = null;
  state.editingRecordId = null;
  state.editingAgencyId=null;
}

const state = {
    currentPage: "endpoints", // 'endpoints' | 'agency-types'
    me: null,
    endpoints: [],
    commands: [],
    ntns: [],
    agencyTypes: [],
    agencies: [],
    
    // Filtering states
    filters: {
        search: "",
        status: "ALL",
        type: "ALL",
        command: "ALL"
    },

    agFilters:{
        search: "",
        command: "ALL",
        ntn: "ALL"
    },

    // Transaction temporary caches
    activeDeleteCallback: null,
    editingRecordId: null,
    editingAgencyId:null
};

// Cache DOM Elements
const DOM = {
    // Navigation
    navItems: document.querySelectorAll(".nav-item"),
    pages: document.querySelectorAll(".content-page"),
    globalSearch: document.getElementById("global-search"),
    btnRefresh: document.getElementById("btn-refresh"),
    
    // Loaders
    contentLoader: document.getElementById("content-loader"),
    
    // Page: Endpoints
    btnNewEndpoint: document.getElementById("btn-add-endpoint"),
    filterSearch: document.getElementById("filter-search"),
    filterStatus: document.getElementById("filter-status"),
    filterType: document.getElementById("filter-type"),
    filterCommand: document.getElementById("filter-command"),
    btnClearFilters: document.getElementById("btn-clear-filters"),
    tableEndpointsBody: document.getElementById("endpoints-tbody"),
    emptyStateEndpoints: document.getElementById("endpoints-empty-state"),

    // Page: Agency Types
    btnNewAgencyType: document.getElementById("btn-add-agency-type"),
    tableAgencyTypesBody: document.getElementById("agency-types-tbody"),
    emptyStateAgencyTypes: document.getElementById("agency-types-empty-state"),

    // Dashboard
    dashboardServiceableCount: document.getElementById("dashboard-serviceable-count"),
    dashboardUnserviceableCount: document.getElementById("dashboard-unserviceable-count"),
    dashboardTypesBody: document.getElementById("dashboard-types-body"),
    dashboardAgenciesBody: document.getElementById("dashboard-agencies-body"),

    //pageAgencies:
    btnNewAgency:document.getElementById("btn-add-agency"),
    agfilterSearch: document.getElementById("ag-filter-search"),
    agfilterCommand: document.getElementById("ag-filter-command"),
    agfilterNTN: document.getElementById("ag-filter-ntn"),
    agbtnClearFilters: document.getElementById("ag-btn-clear-filters"),
    tableAgenciesBody:document.getElementById("agencies-tbody"),
    emptyStateAgencies: document.getElementById("agency-empty-state"),
    

    // Modal: Endpoint
    modalEndpoint: document.getElementById("modal-endpoint"),
    formEndpoint: document.getElementById("form-endpoint"),
    modalEndpointTitle: document.getElementById("endpoint-modal-title"),
    formEndpointIsEdit: document.getElementById("form-endpoint-is-edit"),
    inputEpId: document.getElementById("ep-id"),
    selectEpType: document.getElementById("ep-type"),
    selectEpStatus: document.getElementById("ep-status"),
    inputEpOwner: document.getElementById("ep-owner"),
    selectEpCommand: document.getElementById("ep-command"),
    selectEpNTN: document.getElementById("ep-ntn"),
    selectEpAgency: document.getElementById("ep-agency"),
    textareaEpRemarks: document.getElementById("ep-remarks"),
    checkboxEpIm: document.getElementById("ep-im"),
    errorEpId: document.getElementById("ep-id-error"),

    // Modal: Agency Type
    modalAgencyType: document.getElementById("modal-agency-type"),
    formAgencyType: document.getElementById("form-agency-type"),
    modalAgencyTypeTitle: document.getElementById("agency-type-modal-title"),
    formAgencyTypeIsEdit: document.getElementById("form-agency-type-is-edit"),
    formAgencyTypeId: document.getElementById("form-agency-type-id"),
    inputAgencyTypeName: document.getElementById("at-name"),
    errorAgencyTypeName: document.getElementById("at-name-error"),

    //Modal: Agency
    modalAgency:document.getElementById("modal-agency"),
    formAgency:document.getElementById("form-agency"),
    modalAgencyTitle:document.getElementById("agency-modal-title"),
    formAgencyIsEdit:document.getElementById("form-agency-is-edit"),
    formAgencyId:document.getElementById("form-agency-id"),
    inputAgencyName:document.getElementById("ag-name"),
    selectAgencyType:document.getElementById("ag-type"),
    selectAgencyCommand:document.getElementById("ag-command"),
    selectAgencyNTN:document.getElementById("ag-ntn"),
    errorAgencyName:document.getElementById("ag-name-error"),

    // Modal: Confirmations
    modalConfirm: document.getElementById("modal-confirm"),
    btnConfirmAction: document.getElementById("btn-confirm-action"),
    confirmMessage: document.getElementById("confirm-message"),

    // Notifications
    toastContainer: document.getElementById("toast-container")
};

// ==========================================
// 4. RENDERING & UI FUNCTIONS
// ==========================================

function showLoading(isVisible) {
    if (isVisible) {
        DOM.contentLoader.classList.remove("hidden");
        DOM.btnRefresh.querySelector("svg").classList.add("icon-spin");
    } else {
        DOM.contentLoader.classList.add("hidden");
        DOM.btnRefresh.querySelector("svg").classList.remove("icon-spin");
    }
}

function showToast(message, type = "success") {
    const toast = document.createElement("div");
    toast.className = `toast toast-${type}`;
    toast.innerHTML = `
        <span class="toast-content">${message}</span>
        <button class="btn-close" style="font-size: 14px;">&times;</button>
    `;
    
    // Quick close listener
    toast.querySelector(".btn-close").addEventListener("click", () => {
        toast.remove();
    });

    DOM.toastContainer.appendChild(toast);
    
    // Auto purge toast
    setTimeout(() => {
        if (toast.parentNode) {
            toast.style.opacity = '0';
            setTimeout(() => toast.remove(), 250);
        }
    }, 4000);
}

// Map complete parent dependency structures
function resolveCommandMapping(agencyId) {
const agency = state.agencies.find(a => a.agencyId === agencyId);
if (!agency) return { agencyName: "", ntnName: "", commandName: "", commandId: "", ntnId: "" };

    const ntn = state.ntns.find(n => n.ntnId === agency.ntnId);
const command = ntn ? state.commands.find(c => c.commandId === ntn.commandId) : null;

return {
    agencyName: agency.agencyName,
    ntnName: ntn ? ntn.ntnName : "Unknown",
    commandName: command ? command.commandName : "Unknown",
    commandId: command ? command.commandId : "",
    ntnId: ntn ? ntn.ntnId : ""
};
}

function resolveAgencyMapping(ntnId,agencyTypeId){
    const agencyType=state.agencyTypes.find(at => at.agencyTypeId===agencyTypeId)
    const ntn=state.ntns.find(n => n.ntnId === ntnId);
    const command=ntn ? state.commands.find(c => c.commandId === ntn.commandId):null; 
return{
    agencyTypeName:agencyType ? agencyType.agencyTypeName : "Unknown",
    ntnName: ntn ? ntn.ntnName : "Unknown",
    commandName: command ? command.commandName : "Unknown",
    commandId: command ? command.commandId : "",
    ntnId: ntn ? ntn.ntnId : "",
    agencyTypeId: agencyType ? agencyType.agencyTypeId: ""
};
}

function renderEndpointsTable() {
    DOM.tableEndpointsBody.innerHTML = "";
    
    // Filtering logic
    const filtered = state.endpoints.filter(ep => {
    const mapping = resolveCommandMapping(ep.agencyId);

    const search = state.filters.search.toLowerCase();

const matchText =
    search === "" ||
    (ep.endpointId && ep.endpointId.toLowerCase().includes(search)) ||
    (ep.systemOwner && ep.systemOwner.toLowerCase().includes(search)) ||
    (ep.remarks && ep.remarks.toLowerCase().includes(search));
    
    const matchStatus =
        state.filters.status === "ALL" ||
        ep.status === state.filters.status;

    const matchType =
        state.filters.type === "ALL" ||
        ep.endpointType === state.filters.type;

    const matchCommand =
        state.filters.command === "ALL" ||
        mapping.commandId === Number(state.filters.command);

    return matchText && matchStatus && matchType && matchCommand;
});

    if (filtered.length === 0) {
        DOM.emptyStateEndpoints.classList.remove("hidden");
    } else {
        DOM.emptyStateEndpoints.classList.add("hidden");
        
        filtered.forEach(ep => {
            //
            try{
            const tr = document.createElement("tr");
            const map = resolveCommandMapping(ep.agencyId);
            
            tr.innerHTML = `
                <td style="font-family: monospace; font-weight: 600; color: var(--accent);">${escapeHTML(ep.endpointId)}</td>
                <td><span class="tag">${escapeHTML(ep.endpointType)}</span></td>
                <td><span class="status-badge ${ep.status.toLowerCase()}">${escapeHTML(ep.status)}</span></td>
                <td>${escapeHTML(ep.systemOwner)}</td>
                <td title="${escapeHTML(map.commandName)}">${truncateString(map.commandName, 22)}</td>
                <td title="${escapeHTML(map.ntnName)}">${truncateString(map.ntnName, 22)}</td>
                <td title="${escapeHTML(map.agencyName)}">${truncateString(map.agencyName, 22)}</td>
                <td title="${escapeHTML(ep.remarks || '')}" style="color: var(--text-secondary); font-style: italic;">
                    ${ep.remarks ? truncateString(ep.remarks, 20) : "-"}
                </td>
                <td>${escapeHTML(ep.imRaised)}</td>
                <td class="text-right">
                    <button class="btn-row-action btn-row-edit" data-id="${ep.endpointId}">Edit</button>
                    <button class="btn-row-action btn-row-delete" data-id="${ep.endpointId}">Delete</button>
                </td>
            `;
            
            // Wire Actions Directly
            tr.querySelector(".btn-row-edit").addEventListener("click", () => handleEditEndpoint(ep));
            tr.querySelector(".btn-row-delete").addEventListener("click", () => handleDeleteEndpointPrompt(ep.endpointId));
            
            DOM.tableEndpointsBody.appendChild(tr);
            }
            catch (err) {
                console.error("Error rendering endpoint:", ep.endpointId, err);
            }
        });
    }
}

function renderAgencyTypesTable() {
    DOM.tableAgencyTypesBody.innerHTML = "";
    
    
    if (state.agencyTypes.length === 0) {
        DOM.emptyStateAgencyTypes.classList.remove("hidden");
    } else {
        DOM.emptyStateAgencyTypes.classList.add("hidden");
        
        state.agencyTypes.forEach(at => {
            const tr = document.createElement("tr");
            tr.innerHTML = `
                <td><strong>${escapeHTML(at.agencyTypeName)}</strong></td>
                <td class="text-right">
                    <button class="btn-row-action btn-row-delete" data-id="${at.agencyTypeId}">Delete</button>
                </td>
            `;
            
            tr.querySelector(".btn-row-delete").addEventListener("click", () => handleDeleteAgencyTypePrompt(at.agencyTypeId));
            DOM.tableAgencyTypesBody.appendChild(tr);
        });
    }
}

function renderAgenciesTable() {
    DOM.tableAgenciesBody.innerHTML = "";
    
    // state.agencies.forEach(agency=>{
    //     console.log(agency);
    // })

    //Filtering Logic
    const filtered=state.agencies.filter(ag=>{
        const mapping=resolveAgencyMapping(ag.ntnId,ag.agencyTypeId);
        const search=state.agFilters.search.toLowerCase();

        const matchText=
        search === "" ||
        (ag.agencyName && ag.agencyName.toLowerCase().includes(search));

        const matchCommand=
        state.agFilters.command === "ALL" ||
        mapping.commandId === Number(state.agFilters.command);
        
        const matchNtn=
        state.agFilters.ntn === "ALL" ||
        mapping.ntnId === Number(state.agFilters.ntn);

        console.log(ag.agencyName+" "+matchText+" "+matchCommand+" "+matchNtn);
        console.log(state.agFilters.ntn);

        return matchText && matchCommand && matchNtn;
    });

    

    if (filtered.length === 0) {
        DOM.emptyStateAgencies.classList.remove("hidden");
    } else {
        DOM.emptyStateAgencies.classList.add("hidden");
        
        filtered.forEach(agency => {
            try{
            const tr = document.createElement("tr");
            const map=resolveAgencyMapping(agency.ntnId,agency.agencyTypeId);

            tr.innerHTML = `
            <td style="font-family: monospace; font-weight: 600; color: var(--accent);">${escapeHTML(agency.agencyName)}</td>
                <td title="${escapeHTML(map.commandName)}">${truncateString(map.commandName, 22)}</td>
                <td title="${escapeHTML(map.ntnName)}">${truncateString(map.ntnName, 22)}</td>
                <td title="${escapeHTML(map.agencyTypeName)}">${truncateString(map.agencyTypeName,20)}</td>
                <td class="text-right">
                    <button class="btn-row-action btn-row-edit" data-id="${agency.agencyID}">Edit</button>
                    <button class="btn-row-action btn-row-delete" data-id="${agency.agencyId}">Delete</button>
                </td>
            `;

            // Wire Actions Directly
            tr.querySelector(".btn-row-edit").addEventListener("click", () => handleEditAgency(agency));
            tr.querySelector(".btn-row-delete").addEventListener("click", () => handleDeleteAgencyPrompt(agency.agencyId));
            DOM.tableAgenciesBody.appendChild(tr);
        }
        catch(err){
            console.error("Error rendering agency:",agency.agencyName,err);
        }
        });
    }
}

function renderDashboard() {
    const serviceableCount = state.endpoints.filter(ep => ep.status === "Serviceable").length;
    const unserviceableCount = state.endpoints.filter(ep => ep.status === "Unserviceable").length;

    const endpointTypeCounts = state.endpoints.reduce((totals, ep) => {
        const type = ep.endpointType || "Unknown";
        totals[type] = (totals[type] || 0) + 1;
        return totals;
    }, {});

    const agencyCounts = state.endpoints.reduce((totals, ep) => {
        const agency = state.agencies.find(a => a.agencyId === ep.agencyId);
        const agencyName = agency ? agency.agencyName : `Unknown (${ep.agencyId})`;
        totals[agencyName] = (totals[agencyName] || 0) + 1;
        return totals;
    }, {});

    DOM.dashboardServiceableCount.textContent = serviceableCount;
    DOM.dashboardUnserviceableCount.textContent = unserviceableCount;

    DOM.dashboardTypesBody.innerHTML = "";
    Object.entries(endpointTypeCounts)
        .sort((a, b) => b[1] - a[1])
        .forEach(([type, count]) => {
            const tr = document.createElement("tr");
            tr.innerHTML = `<td>${escapeHTML(type)}</td><td>${count}</td>`;
            DOM.dashboardTypesBody.appendChild(tr);
        });

    DOM.dashboardAgenciesBody.innerHTML = "";
    Object.entries(agencyCounts)
        .sort((a, b) => b[1] - a[1])
        .forEach(([agencyName, count]) => {
            const tr = document.createElement("tr");
            tr.innerHTML = `<td>${escapeHTML(agencyName)}</td><td>${count}</td>`;
            DOM.dashboardAgenciesBody.appendChild(tr);
        });
}

function renderCommandFilterOptions() {
    // Keep initial "All" value
    DOM.filterCommand.innerHTML = `<option value="ALL">All Commands</option>`;
    state.commands.forEach(c => {
        const opt = document.createElement("option");
        opt.value = c.commandId;
        opt.textContent = c.commandName;
        DOM.filterCommand.appendChild(opt);
    });
}

function renderAgency_CommandFilterOptions() {
    // Keep initial "All" value
    DOM.agfilterCommand.innerHTML = `<option value="ALL">All Commands</option>`;
    state.commands.forEach(c => {
        const opt = document.createElement("option");
        opt.value = c.commandId;
        opt.textContent = c.commandName;
        DOM.agfilterCommand.appendChild(opt);
    });
}

function renderAgency_NtnFilterOptions() {
    // Keep initial "All" value
    DOM.agfilterNTN.innerHTML = `<option value="ALL">All NTNs</option>`;
    state.ntns.forEach(n => {
        const opt = document.createElement("option");
        opt.value = n.ntnId;
        opt.textContent = n.ntnName;
        DOM.agfilterNTN.appendChild(opt);
    });
}

// Dependent Dropdown Rendering Engine
function populateCommandDropdown(selectElement, selectedValue = null) {

    if (selectedValue !== null && selectedValue !== "") {
        selectedValue = Number(selectedValue);
    }
    selectElement.innerHTML = `<option value="">Select Command</option>`;
    state.commands.forEach(command => {
        const option = document.createElement("option");
        option.value = command.commandId;
        option.textContent = command.commandName;
        if (command.commandId === selectedValue) option.selected = true;
        selectElement.appendChild(option);
    });
}

function populateNTNDropdown(selectElement, commandId, selectedValue = null) {
    if(selectedValue !== null && selectedValue !== "") {
        selectedValue = Number(selectedValue);
    }
    if(commandId !== null && commandId !== "") {
        commandId = Number(commandId);
    }
    selectElement.innerHTML = `<option value="">Select NTN</option>`;
    
    if (!commandId) {
        selectElement.disabled = true;
        return;
    }

    const filtered = state.ntns.filter(ntn => ntn.commandId === commandId);
    
    filtered.forEach(ntn => {
        const option = document.createElement("option");
        option.value = ntn.ntnId;
        option.textContent = ntn.ntnName;
        if (ntn.ntnId === selectedValue) option.selected = true;
        selectElement.appendChild(option);
    });
    
    selectElement.disabled = false;
}

function populateAgencyTypeDropdown(selectElement, selectedValue = null) {

    if (selectedValue !== null && selectedValue !== "") {
        selectedValue = Number(selectedValue);
    }
    selectElement.innerHTML = `<option value="">Select Agency Type</option>`;
    state.agencyTypes.forEach(at => {
        const option = document.createElement("option");
        option.value = at.agencyTypeId;
        option.textContent = at.agencyTypeName;
        if (at.agencyTypeId === selectedValue) option.selected = true;
        selectElement.appendChild(option);
    });
}

function populateAgencyDropdown(selectElement, ntnId, selectedValue = null) {
    if(ntnId !== null && ntnId !== "") {
        ntnId = Number(ntnId);
    }
    if(selectedValue !== null && selectedValue !== "") {
        selectedValue = Number(selectedValue);
    }
    
    selectElement.innerHTML = `<option value="">Select Agency</option>`;
    
    if (!ntnId) {
        selectElement.disabled = true;
        return;
    }

    const filtered = state.agencies.filter(a => a.ntnId === ntnId);
    
    filtered.forEach(a => {
        const option = document.createElement("option");
        option.value = a.agencyId;
        option.textContent = a.agencyName;
        if (a.agencyId === selectedValue) option.selected = true;
        selectElement.appendChild(option);
    });
    
    selectElement.disabled = false;
}

// Navigation Coordinator
function switchPage(targetPageId) {
    state.currentPage = targetPageId;
    
    DOM.pages.forEach(p => p.classList.add("hidden"));
    DOM.navItems.forEach(item => {
        if (item.dataset.target === targetPageId) {
            item.classList.add("active");
        } else {
            item.classList.remove("active");
        }
    });

    const activePage = document.getElementById(`page-${targetPageId}`);
    if (activePage) activePage.classList.remove("hidden");
}

// ==========================================
// 5. EVENT LISTENERS & INTERACTION HANDLERS
// ==========================================

function initNavigation() {
    DOM.navItems.forEach(item => {
        item.addEventListener("click", () => {
            if (item.hasAttribute("disabled")) return;
            switchPage(item.dataset.target);
        });
    });

    // Unified Refresh Button Trigger
    DOM.btnRefresh.addEventListener("click", reloadData);
}

function initFilters() {
    // Live Endpoint search filter
    DOM.filterSearch.addEventListener("input", (e) => {
        state.filters.search = e.target.value;
        renderEndpointsTable();
    });

    // Global Topbar Search
    DOM.globalSearch.addEventListener("input", (e) => {
        state.filters.search = e.target.value;
        DOM.filterSearch.value = e.target.value; // Sync page-specific search input
        if (state.currentPage !== "endpoints") {
            switchPage("endpoints");
        }
        renderEndpointsTable();
    });

    DOM.agfilterSearch.addEventListener("input",(e)=>{
        state.agFilters.search=e.target.value;
        renderAgenciesTable();
    });

    DOM.filterStatus.addEventListener("change", (e) => {
        state.filters.status = e.target.value;
        renderEndpointsTable();
    });

    DOM.filterType.addEventListener("change", (e) => {
        state.filters.type = e.target.value;
        renderEndpointsTable();
    });

    DOM.filterCommand.addEventListener("change", (e) => {
        state.filters.command = e.target.value;
        renderEndpointsTable();
    });

    DOM.agfilterCommand.addEventListener("change", (e) => {
        state.agFilters.command = e.target.value;
        renderAgenciesTable();
    });

    DOM.agfilterNTN.addEventListener("change", (e) => {
        state.agFilters.ntn = e.target.value;
        renderAgenciesTable();
    });

    DOM.btnClearFilters.addEventListener("click", () => {
        clearEndpointFilters();
        showToast("Filter parameters reset.", "success");
    });

    DOM.agbtnClearFilters.addEventListener("click", () => {
        DOM.agfilterSearch.value = "";
        DOM.globalSearch.value = "";
        DOM.agfilterCommand.value = "ALL";
        DOM.agfilterNTN.value = "ALL";
        
        state.agFilters = {
            search: "",
            command: "ALL",
            ntn:"ALL"
        };
        
        renderAgenciesTable();
        showToast("Filter parameters reset.", "success");
    });
}
function clearEndpointFilters() {
DOM.filterSearch.value = "";
        DOM.globalSearch.value = "";
        DOM.filterStatus.value = "ALL";
        DOM.filterType.value = "ALL";
        DOM.filterCommand.value = "ALL";
        
        state.filters = {
            search: "",
            status: "ALL",
            type: "ALL",
            command: "ALL"
        };
        
        renderEndpointsTable();
}
// Dependancy Dropdown Event Listeners
function initDependentDropdowns() {
    DOM.selectEpCommand.addEventListener("change", (e) => {
        populateNTNDropdown(DOM.selectEpNTN, Number(e.target.value));
        populateAgencyDropdown(DOM.selectEpAgency, null); // Force clear child level
    });

    DOM.selectEpNTN.addEventListener("change", (e) => {
        populateAgencyDropdown(DOM.selectEpAgency, Number(e.target.value));
    });

    DOM.selectAgencyCommand.addEventListener("change", (e)=>{
        populateNTNDropdown(DOM.selectAgencyNTN,Number(e.target.value));
    })
}

// Modal Handlers
function openModal(modalBackdrop) {
    modalBackdrop.classList.remove("hidden");
}

function closeModal(modalBackdrop) {
    modalBackdrop.classList.add("hidden");
    clearValidationErrors();
}

function clearValidationErrors() {
    DOM.errorEpId.textContent = "";
    DOM.inputEpId.style.borderColor = "";
    DOM.errorAgencyTypeName.textContent = "";
    DOM.inputAgencyTypeName.style.borderColor = "";
    DOM.errorAgencyName.textContent="";
    DOM.inputAgencyName.style.borderColor="";

}

function initModals() {
    // Backdrop clicks close modal
    document.querySelectorAll(".modal-backdrop").forEach(backdrop => {
        backdrop.addEventListener("click", (e) => {
            if (e.target === backdrop) closeModal(backdrop);
        });
        
        // Find cancel and close buttons inside
        backdrop.querySelectorAll(".btn-cancel, .btn-close").forEach(btn => {
            btn.addEventListener("click", () => closeModal(backdrop));
        });
    });

    // Endpoint Open Trigger
    DOM.btnNewEndpoint.addEventListener("click", () => {
        DOM.formEndpoint.reset();
        DOM.formEndpointIsEdit.value = "false";
        DOM.modalEndpointTitle.textContent = "Register Endpoint";
        DOM.inputEpId.disabled = false;
        
        populateCommandDropdown(DOM.selectEpCommand);
        populateNTNDropdown(DOM.selectEpNTN, null);
        populateAgencyDropdown(DOM.selectEpAgency, null);

        openModal(DOM.modalEndpoint);
    });

    // Agency Type Open Trigger
    DOM.btnNewAgencyType.addEventListener("click", () => {
        DOM.formAgencyType.reset();
        DOM.formAgencyTypeIsEdit.value = "false";
        DOM.modalAgencyTypeTitle.textContent = "Add Agency Type";
        
        openModal(DOM.modalAgencyType);
    });

    DOM.btnNewAgency.addEventListener("click", () => {
        DOM.formAgency.reset();
        DOM.formAgencyIsEdit.value="false";
        DOM.modalAgencyTitle.textContent="Add Agency";

        populateCommandDropdown(DOM.selectAgencyCommand);
        populateNTNDropdown(DOM.selectAgencyNTN,null);
        populateAgencyTypeDropdown(DOM.selectAgencyType);

        openModal(DOM.modalAgency);
    });

    // Submit Action Handlers
    DOM.formEndpoint.addEventListener("submit", handleEndpointFormSubmit);
    DOM.formAgencyType.addEventListener("submit", handleAgencyTypeFormSubmit);
    DOM.formAgency.addEventListener("submit", handleAgencyFormSubmit);
}

// ==========================================
// 6. BUSINESS LOGIC OPERATIONS
// ==========================================


// LOGIN FUNCTIONALITY
async function login(username, password){

    const response = await fetch(LOGIN_URL,{
        method:"POST",
        headers:{
            "Content-Type":"application/json"
        },
        body:JSON.stringify({
            username,
            password
        })
    });

    if(!response.ok){
        throw new Error("Invalid username or password");
    }

    const data = await response.json();
    auth.save(data);

    await afterAuthSuccess();
}

document
.getElementById("login-form")
.addEventListener("submit",async e=>{

    e.preventDefault();

    try{
        await login(
            document.getElementById("login-username").value,
            document.getElementById("login-password").value
        );
    }

    catch(err){
        document.getElementById("login-error").textContent=
            err.message;
    }
});

//LOGOUT BUTTON
document
.getElementById("btn-logout")
.addEventListener("click",()=>{
    auth.logout();
});



async function handleEndpointFormSubmit(e) {
    e.preventDefault();
    clearValidationErrors();

    const isEdit = DOM.formEndpointIsEdit.value === "true";
    const endpointId = DOM.inputEpId.value.trim();

    // Structural Validation (Validate pattern input ID)
    if (!/^[a-zA-Z0-9-_]+$/.test(endpointId)) {
        DOM.errorEpId.textContent = "Characters allowed: letters, numbers, hyphens, and underscores.";
        DOM.inputEpId.style.borderColor = "var(--danger)";
        return;
    }
    const payload = {
        endpointId: endpointId,
        endpointType: DOM.selectEpType.value,
        status: DOM.selectEpStatus.value,
        systemOwner: DOM.inputEpOwner.value.trim(),
        remarks: DOM.textareaEpRemarks.value.trim(),
        imRaised: DOM.checkboxEpIm.value,
        agencyId: Number(DOM.selectEpAgency.value)
    };
    try {
        showLoading(true);
        if (isEdit) {
            await api.updateEndpoint(state.editingRecordId, payload);
            showToast(`Endpoint ${endpointId} successfully configured.`);
        } else {
            await api.createEndpoint(payload);
            showToast(`Endpoint ${endpointId} registered to ledger.`);
        }
        
        closeModal(DOM.modalEndpoint);
        await reloadData();
    } catch (err) {
        showToast(err.message, "error");
        if (err.message.includes("already exists")) {
            DOM.errorEpId.textContent = err.message;
            DOM.inputEpId.style.borderColor = "var(--danger)";
        }
    } finally {
        showLoading(false);
    }
}

async function handleAgencyTypeFormSubmit(e) {
    e.preventDefault();
    clearValidationErrors();

    const payload = {
        agencyTypeName: DOM.inputAgencyTypeName.value.trim()
    };

    try {
        showLoading(true);
        await api.createAgencyType(payload);
        showToast(`Agency type "${payload.agencyTypeName}" added.`);
        
        closeModal(DOM.modalAgencyType);
        await reloadData();
    } catch (err) {
        showToast(err.message, "error");
        DOM.errorAgencyTypeName.textContent = err.message;
        DOM.inputAgencyTypeName.style.borderColor = "var(--danger)";
    } finally {
        showLoading(false);
    }
}

async function handleAgencyFormSubmit(e) {
    e.preventDefault();
    clearValidationErrors();

    const isEdit = DOM.formAgencyIsEdit.value === "true";
    const agencyName= DOM.inputAgencyName.value.trim();

    // Structural Validation (Validate pattern input ID)
    if (!/^[a-zA-Z0-9-_]+$/.test(agencyName)) {
        DOM.errorAgencyName.textContent = "Characters allowed: letters, numbers, hyphens, and underscores.";
        DOM.inputAgencyName.style.borderColor = "var(--danger)";
        return;
    }
    const payload = {
        agencyName: agencyName,
        agencyTypeId: Number(DOM.selectAgencyType.value),
        ntnId: Number(DOM.selectAgencyNTN.value)
    };
    try {
        showLoading(true);
        if (isEdit) {
            await api.updateAgency(state.editingAgencyId, payload);
            showToast(`Agency ${agencyName} successfully configured.`);
        } else {
            await api.createAgency(payload);
            showToast(`Agency ${agencyName} added successfully.`);
        }
        
        closeModal(DOM.modalAgency);
        await reloadData();
    } catch (err) {
        showToast(err.message, "error");
        if (err.message.includes("already exists")) {
            DOM.errorAgencyName.textContent = err.message;
            DOM.inputAgencyName.style.borderColor = "var(--danger)";
        }
    } finally {
        showLoading(false);
    }
}

function handleEditEndpoint(endpoint) {
    DOM.formEndpointIsEdit.value = "true";
    state.editingRecordId = endpoint.endpointId;
    DOM.modalEndpointTitle.textContent = `Edit Endpoint Configuration (${endpoint.endpointId})`;
    
    // Set Fields
    DOM.inputEpId.value = endpoint.endpointId;
    DOM.inputEpId.disabled = true; // Lock identity key primary ID
    DOM.selectEpType.value = endpoint.endpointType;
    DOM.selectEpStatus.value = endpoint.status;
    DOM.inputEpOwner.value = endpoint.systemOwner;
    DOM.textareaEpRemarks.value = endpoint.remarks || "";
    DOM.checkboxEpIm.value = endpoint.imRaised;

    // Resolve structural hierarchy
    const agencyID =
    endpoint.agencyId != null && endpoint.agencyId !== ""
        ? Number(endpoint.agencyId)
        : null;
    const map = resolveCommandMapping(agencyID);
    const agencyRecord = state.agencies.find(a => a.agencyId === agencyID);
    
    populateCommandDropdown(DOM.selectEpCommand, map.commandId);
    
    if (map.commandId && agencyRecord) {
        populateNTNDropdown(DOM.selectEpNTN, map.commandId, agencyRecord.ntnId);
        populateAgencyDropdown(DOM.selectEpAgency, agencyRecord.ntnId, agencyID);
    } else {
        populateNTNDropdown(DOM.selectEpNTN, null);
        populateAgencyDropdown(DOM.selectEpAgency, null);
    }

    openModal(DOM.modalEndpoint);
}

function handleEditAgency(agency) {
    DOM.formAgencyIsEdit.value = "true";
    state.editingAgencyId = agency.agencyId;
    DOM.modalAgencyTitle.textContent = `Edit Agency Configuration (${agency.agencyName})`;
    
    // Set Fields
    DOM.inputAgencyName.value = agency.agencyName;
    //DOM.inputEpId.disabled = true; // Lock agency Name for editing

    // Resolve structural hierarchy
    const ntnID=agency.ntnId != null && agency.ntnId !== ""
        ? Number(agency.ntnId)
        : null;
    const agencyTypeID=agency.agencyTypeId != null && agency.agencyTypeId !== ""
        ? Number(agency.agencyTypeId)
        : null;
    
    const map = resolveAgencyMapping(ntnID,agencyTypeID);
    
    populateCommandDropdown(DOM.selectAgencyCommand, map.commandId);
    populateAgencyTypeDropdown(DOM.selectAgencyType, map.agencyTypeId)
    if (map.commandId && agency) {
        populateNTNDropdown(DOM.selectAgencyNTN, map.commandId, ntnID);
        //populateAgencyDropdown(DOM.selectEpAgency, ntnID, agencyID);
    } else {
        populateNTNDropdown(DOM.selectAgencyNTN, null);
        //populateAgencyDropdown(DOM.selectEpAgency, null);
    }

    openModal(DOM.modalAgency);
}

function handleDeleteEndpointPrompt(id) {
    DOM.confirmMessage.textContent = `This action permanently deletes endpoint record "${id}". Are you sure you want to proceed?`;
    DOM.btnConfirmAction.className = "btn btn-danger";
    DOM.btnConfirmAction.textContent = "Confirm Delete";
    
    state.activeDeleteCallback = async () => {
        try {
            showLoading(true);
            await api.deleteEndpoint(id);
            showToast(`Endpoint ${id} has been expunged from register.`);
            await reloadData();
        } catch (err) {
            showToast(err.message, "error");
        } finally {
            showLoading(false);
        }
    };
    
    openModal(DOM.modalConfirm);
}

function handleDeleteAgencyTypePrompt(id) {
    if(id!==null &&id!==""){
        id=Number(id);
    }
    const at = state.agencyTypes.find(a => a.agencyTypeId === id);
    const name = at ? at.agencyTypeName : id;
    
    DOM.confirmMessage.textContent = `Are you sure you want to delete agency type "${name}"? This structural alteration is irreversible.`;
    DOM.btnConfirmAction.className = "btn btn-danger";
    DOM.btnConfirmAction.textContent = "Confirm Delete";

    state.activeDeleteCallback = async () => {
        try {
            showLoading(true);
            await api.deleteAgencyType(id);
            showToast(`Agency type classification "${name}" cleared.`);
            await reloadData();
        } catch (err) {
            showToast(err.message, "error");
        } finally {
            showLoading(false);
        }
    };

    openModal(DOM.modalConfirm);
}

function handleDeleteAgencyPrompt(id) {
    if(id!==null &&id!==""){
        id=Number(id);
    }
    const agency = state.agencies.find(a => a.agencyId === id);
    const name = agency ? agency.agencyName : id;
    
    DOM.confirmMessage.textContent = `Are you sure you want to delete agency "${name}"? This alteration is irreversible.`;
    DOM.btnConfirmAction.className = "btn btn-danger";
    DOM.btnConfirmAction.textContent = "Confirm Delete";

    state.activeDeleteCallback = async () => {
        try {
            showLoading(true);
            await api.deleteAgency(id);
            showToast(`Agency "${name}" cleared.`);
            await reloadData();
        } catch (err) {
            showToast(err.message, "error");
        } finally {
            showLoading(false);
        }
    };

    openModal(DOM.modalConfirm);
}


// Global confirm action receiver
DOM.btnConfirmAction.addEventListener("click", async () => {
    if (state.activeDeleteCallback) {
        const callback = state.activeDeleteCallback;
        state.activeDeleteCallback = null;
        closeModal(DOM.modalConfirm);
        await callback();
    }
});

// ==========================================
// 7. INITIALIZATION ENGINE
// ==========================================

// async function reloadData() {
//     showLoading(true);
//     try {
//         // Parallel REST request simulation
//         const [eps, commands, ntns, agencyTypes, agencies] = await Promise.all([
//             api.getEndpoints(),
//             api.getCommands(),
//             api.getNTNs(),
//             api.getAgencyTypes(),
//             api.getAgencies()
//         ]);

//         state.endpoints = eps;
//         state.commands = commands;
//         state.ntns = ntns;
//         state.agencyTypes = agencyTypes;
//         state.agencies = agencies;

//         // Render UI Components
//         renderCommandFilterOptions();
//         renderAgency_CommandFilterOptions();
//         renderAgency_NtnFilterOptions();
//         renderEndpointsTable();
//         renderAgencyTypesTable();
//         renderAgenciesTable();
//     } catch (err) {
//         showToast("System failed to synchronise state with core catalog: " + err.message, "error");
//     } finally {
//         showLoading(false);
//     }
// }

async function bootstrapForRole() {

  try {
    const me = await api.getCurrentUser();
    state.me = me;

    // Seed what /api/me already gives you
    if (me.commandId && me.commandName) {
      state.commands = [{
        commandId: me.commandId,
        commandName: me.commandName
      }];
    } else {
      state.commands = [];
    }

    if (me.ntnId && me.ntnName) {
      state.ntns = [{
        ntnId: me.ntnId,
        ntnName: me.ntnName,
        commandId: me.commandId || null,
        commandName: me.commandName
      }];
    } else {
      state.ntns = [];
    }

    if (me.agencyId && me.agencyName) {
      state.agencies = [{
        agencyId: me.agencyId,
                agencyName: me.agencyName,
                agencyTypeId:me.agencyTypeId,
                agencyTypeName:me.agencyTypeName,
                ntnId: me.ntnId,
                ntnName: me.ntnName
      }];
    } else {
      state.agencies = [];
    }

    // Default behavior by role
    if (me.role === 'SUPER_ADMIN' || me.role === 'ADMIN') {
      const [commands, ntns, agencyTypes, agencies, eps] = await Promise.all([
        api.getCommands(),
        api.getNTNs(),
        api.getAgencyTypes(),
        api.getAgencies(),
        api.getEndpoints(),
      ]);

      state.commands = commands;
      state.ntns = ntns;
      state.agencyTypes = agencyTypes;
      state.agencies = agencies;
      state.endpoints = eps;
    } else if (me.role === 'COMMAND_HEAD') {
      const [ntns, agencyTypes, agencies, eps] = await Promise.all([
        api.getNTNs(),
        api.getAgencyTypes(),
        api.getAgencies(),
        api.getEndpoints(),
      ]);

      state.ntns = ntns;
      state.agencyTypes = agencyTypes;
      state.agencies = agencies;
      state.endpoints = eps;
    } else if (me.role === 'NTN_HEAD') {
      const [agencyTypes, agencies, eps] = await Promise.all([
        api.getAgencyTypes(),
        api.getAgencies(),
        api.getEndpoints(),
      ]);

      state.agencyTypes = agencyTypes;
      state.agencies = agencies;
      state.endpoints = eps;
    } else if (me.role === 'AGENCY_HEAD') {
      const [agencyTypes, eps] = await Promise.all([
        api.getAgencyTypes(),
        api.getEndpoints(),
      ]);

      state.agencyTypes = agencyTypes;
      state.endpoints = eps;
    }
    resetFilterUIAndState();
    renderCommandFilterOptions();
    renderAgency_CommandFilterOptions();
    renderAgency_NtnFilterOptions();
    renderDashboard();
    renderEndpointsTable();
    renderAgencyTypesTable();
    renderAgenciesTable();

  } catch (err) {
    resetAppState();
    showToast('System failed to synchronise state: ' + err.message, 'error');
    throw err;
  } 
}

async function afterAuthSuccess() {
    // IMPORTANT: clear old role state before loading the new one
    resetAppState();

clearEndpointFilters();
switchPage('dashboard');

    // Load current user's scope first
    await bootstrapForRole();

  document.getElementById('login-page').classList.add('hidden');
  document.getElementById('app-container').classList.remove('hidden');
}

async function reloadData() {
  showLoading(true);
  const requestedPage = state.currentPage || 'endpoints';
  try {
    resetAppState();
    state.currentPage = requestedPage;
    clearEndpointFilters();
    await bootstrapForRole();
    resetFilterUIAndState();
    renderDashboard();
    switchPage(requestedPage);
    renderEndpointsTable();
    renderAgencyTypesTable();
    renderAgenciesTable();
  } catch (err) {
    showToast('System failed to synchronise state: ' + err.message, 'error');
  } finally {
    showLoading(false);
  }
}



// Utility Helpers
function escapeHTML(str) {
    if (!str) return "";
    return str.replace(/[&<>'"]/g, 
        tag => ({ '&': '&amp;', '<': '&lt;', '>': '&gt;', "'": '&#39;', '"': '&quot;' }[tag] || tag)
    );
}

function truncateString(str, num) {
    if (!str) return "";
    if (str.length <= num) return str;
    return str.slice(0, num) + "...";
}

// Bootstrap Application Runtime
document.addEventListener("DOMContentLoaded", async () => {
    initNavigation();
    initFilters();
    initDependentDropdowns();
    initModals();
    
    // Initial Fetch cycle
    if(auth.isLoggedIn()){
        await afterAuthSuccess();
    }
    else{
        document.getElementById("login-page").classList.remove("hidden");
        document.getElementById("app-container").classList.add("hidden");
    }
});