
const pageSizeField = document.getElementById('size');
const nextButton = document.getElementById('next-btn');
const previousButton = document.getElementById('prev-btn');
const tBody = document.getElementById('tbody');
const addButton = document.getElementById('add-btn');
const syncButton = document.getElementById('sync-btn');
const searchButton = document.getElementById('search-button');
const searchField = document.getElementById('search-element');
const searchTypeField = document.getElementById('search-by');
const logoutButton = document.getElementById('logout');
const firstNameHeading = document.getElementById('firstNameHeading');
const lastNameHeading = document.getElementById('lastNameHeading');
const addressHeading = document.getElementById('addressHeading');
const cityHeading = document.getElementById('cityHeading');
const stateHeading = document.getElementById('stateHeading');
const emailHeading = document.getElementById('emailHeading');
const phoneHeading = document.getElementById('phoneHeading');
let currentPageNumber = 0;
let field = "id";
let pageSize = 5;
/*
//On click to sort based on firstName
firstNameHeading.addEventListener('click',(event)=>{
    event.preventDefault();
    field = "first_name";
    getCustomerListWithPaginationAndSort(pageSize,currentPageNumber,field)
})
    */

//On click to sort based on last name
lastNameHeading.addEventListener('click',(event)=>{
    event.preventDefault();
    field = "last_name";
    getCustomerListWithPaginationAndSort(pageSize,currentPageNumber,field);
})


//On click to sort based on address
addressHeading.addEventListener('click',(event)=>{
    event.preventDefault();
    field = "address";
    getCustomerListWithPaginationAndSort(pageSize,currentPageNumber,field);
})

//On click to sort based on city
cityHeading.addEventListener('click',(event)=>{
    event.preventDefault();
    field = "city";
    getCustomerListWithPaginationAndSort(pageSize,currentPageNumber,field);
})

//On click to sort based on state
stateHeading.addEventListener('click',(event)=>{
    event.preventDefault();
    field = "state";
    getCustomerListWithPaginationAndSort(pageSize,currentPageNumber,field);
})

//On click to sort based on email
emailHeading.addEventListener('click',(event)=>{
    event.preventDefault();
    field = "email";
    getCustomerListWithPaginationAndSort(pageSize,currentPageNumber,field);
})

//On click to sort based on phone
phoneHeading.addEventListener('click',(event)=>{
    event.preventDefault();
    field = "phone";
    getCustomerListWithPaginationAndSort(pageSize,currentPageNumber,field);
})

logoutButton.addEventListener('click',(event)=>{
    localStorage.clear();
    window.location.href = 'index.html';
})

nextButton.addEventListener('click',(event)=>{
    event.preventDefault();
    currentPageNumber++;
    console.log("Current Page Number Inside next: ",currentPageNumber);
    pageSize = pageSizeField.value;
    console.log(pageSizeField.value);
    previousButton.style.backgroundColor = 'blueviolet';
    getCustomerListWithPaginationAndSort(pageSize,currentPageNumber,field); 

})

previousButton.addEventListener('click',(event)=>{
    event.preventDefault();
    currentPageNumber--;
    if(currentPageNumber<0){
        currentPageNumber = 0;
    }
    console.log("Current Page Number Inside previous: ",currentPageNumber);
    pageSize = pageSizeField.value;
    nextButton.style.backgroundColor = 'blueviolet';
    getCustomerListWithPaginationAndSort(pageSize,currentPageNumber,field);
})

addButton.addEventListener('click',(event)=>{
    event.preventDefault();
    window.location.href = 'addCustomer.html';
})

searchButton.addEventListener('click',(event)=>{
    event.preventDefault();
    searchTerm = searchField.value;
    searchType = searchTypeField.value;
    searchCustomer(searchType,searchTerm);


})

syncButton.addEventListener('click',(event)=>{
    event.preventDefault();
    syncCustomerData();
})


function searchCustomer(searchType,searchTerm){

    // if(searchType==="first_name"){
    //     getCustomerListByFirstName(searchTerm);
    // }
    if(searchType==="city"){
        getCustomerListByCity(searchTerm);
    }
    else if(searchType==="email"){
        getCustomerListByEmail(searchTerm);
    }
    else if(searchType==="phone"){
        getCustomerListByPhone(searchTerm);
    }
    else{
        alert("Select correct search type");
    }
    
}

function onEditClick(customerId){
    window.location.href = 'updateCustomer.html?customerId=' + customerId;
}

async function onDeleteClick(customerId){
    try{
        const token = localStorage.getItem('authToken');
        await fetch(`http://localhost:8080/customer/delete/${customerId}`,{
            method: 'DELETE',
            headers: {
                'Authorization':`Bearer ${token}`
            }
        }
        );

        getCustomerList();   
        }
    catch(error){
            console.error('Error:',error);
        }

        
}

async function syncCustomerData() {
    const token = localStorage.getItem('authToken');
    console.log("Token inside syncdata function:",token);
    try {
        const response = await fetch('http://localhost:8080/customer/sync', {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });

        if (!response.ok) {
            throw new Error('Network response was not ok');
        }

        const data = await response.text();
        console.log('Success:', data);
        alert('Customer synchronization triggered successfully');
    } catch (error) {
        console.error('Error:', error);
        alert('Error triggering customer synchronization');
    }
}




async function getCustomerListByFirstName(searchTerm){
    try{
        const token = localStorage.getItem('authToken');
        const response = await fetch(`http://localhost:8080/customer/getByFirstName/${searchTerm}`,{
            headers: {
                'Authorization':`Bearer ${token}`
            }
        }
        );

        console.log("Inside pagination fucntion",response);
        console.log("Inside pagination fucntion",response.ok);
        if(!response.ok){
            window.location.href = 'index.html';
        }
        let customerList = await response.json();
        


        
        
            
            tBody.innerHTML = ``;
            for(let i=0;i<customerList.length;i++){
                customer = customerList[i];
                editButtonId = `edit-btn-${customer.id}`;
                deleteButtonId = `delete-btn-${customer.id}`;
                tBody.innerHTML += `
                    <tr id="${customer.id}">
                        <td>${customer.first_name}</td>
                        <td>${customer.last_name}</td>
                        <td>${customer.street}</td>
                        <td>${customer.city}</td>
                        <td>${customer.state}</td>
                        <td>${customer.email}</td>
                        <td>${customer.phone}</td>
                        <td><button onclick= "onEditClick(${customer.id})" class="edit-btn" id="${editButtonId}">Edit</button><button onclick="onDeleteClick(${customer.id})" class="delete-btn" id="${deleteButtonId}">Delete</button> </td>
                    </tr>
            `
            
        }
        
        
    }catch(error){
        console.error('Error:',error);
    }
}


async function getCustomerListByEmail(searchTerm){
    try{
        const token = localStorage.getItem('authToken');
        const response = await fetch(`http://localhost:8080/customer/getByEmail/${searchTerm}`,{
            headers: {
                'Authorization':`Bearer ${token}`
            }
        }
        );

        console.log("Inside pagination fucntion",response);
        console.log("Inside pagination fucntion",response.ok);
        if(!response.ok){
            window.location.href = 'index.html';
        }
        let customerList = await response.json();
        


        
        
            
            tBody.innerHTML = ``;
            for(let i=0;i<customerList.length;i++){
                customer = customerList[i];
                editButtonId = `edit-btn-${customer.id}`;
                deleteButtonId = `delete-btn-${customer.id}`;
                tBody.innerHTML += `
                    <tr id="${customer.id}">
                        <td>${customer.first_name}</td>
                        <td>${customer.last_name}</td>
                        <td>${customer.street}</td>
                        <td>${customer.city}</td>
                        <td>${customer.state}</td>
                        <td>${customer.email}</td>
                        <td>${customer.phone}</td>
                        <td><button onclick= "onEditClick(${customer.id})" class="edit-btn" id="${editButtonId}">Edit</button><button onclick="onDeleteClick(${customer.id})" class="delete-btn" id="${deleteButtonId}">Delete</button> </td>
                    </tr>
            `
            
        }
        
        
    }catch(error){
        console.error('Error:',error);
    }
}

async function getCustomerListByCity(searchTerm){
    try{
        const token = localStorage.getItem('authToken');
        const response = await fetch(`http://localhost:8080/customer/getByCity/${searchTerm}`,{
            headers: {
                'Authorization':`Bearer ${token}`
            }
        }
        );

        console.log("Inside pagination fucntion",response);
        console.log("Inside pagination fucntion",response.ok);
        if(!response.ok){
            window.location.href = 'index.html';
        }
        let customerList = await response.json();
        


        
        
            
            console.log(customerList);
            tBody.innerHTML = ``;
            for(let i=0;i<customerList.length;i++){
                customer = customerList[i];
                editButtonId = `edit-btn-${customer.id}`;
                deleteButtonId = `delete-btn-${customer.id}`;
                tBody.innerHTML += `
                    <tr id="${customer.id}">
                        <td>${customer.first_name}</td>
                        <td>${customer.last_name}</td>
                        <td>${customer.street}</td>
                        <td>${customer.city}</td>
                        <td>${customer.state}</td>
                        <td>${customer.email}</td>
                        <td>${customer.phone}</td>
                        <td><button onclick= "onEditClick(${customer.id})" class="edit-btn" id="${editButtonId}">Edit</button><button onclick="onDeleteClick(${customer.id})" class="delete-btn" id="${deleteButtonId}">Delete</button> </td>
                    </tr>
            `
            
            
        }
        
        
    }catch(error){
        console.error('Error:',error);
    }
}


async function getCustomerListByPhone(searchTerm){
    try{
        const token = localStorage.getItem('authToken');
        const response = await fetch(`http://localhost:8080/customer/getByPhone/${searchTerm}`,{
            headers: {
                'Authorization':`Bearer ${token}`
            }
        }
        );

        console.log("Inside pagination fucntion",response);
        console.log("Inside pagination fucntion",response.ok);
        if(!response.ok){
            window.location.href = 'index.html';
        }
        let customerList = await response.json();
        


        
        
            
            tBody.innerHTML = ``;
            for(let i=0;i<customerList.length;i++){
                customer = customerList[i];
                editButtonId = `edit-btn-${customer.id}`;
                deleteButtonId = `delete-btn-${customer.id}`;
                tBody.innerHTML += `
                    <tr id="${customer.id}">
                        <td>${customer.first_name}</td>
                        <td>${customer.last_name}</td>
                        <td>${customer.street}</td>
                        <td>${customer.city}</td>
                        <td>${customer.state}</td>
                        <td>${customer.email}</td>
                        <td>${customer.phone}</td>
                        <td><button onclick= "onEditClick(${customer.id})" class="edit-btn" id="${editButtonId}">Edit</button><button onclick="onDeleteClick(${customer.id})" class="delete-btn" id="${deleteButtonId}">Delete</button> </td>
                    </tr>
            `
            
            
        }
        
        
    }catch(error){
        console.error('Error:',error);
    }
}




async function getCustomerListWithPaginationAndSort(pageSize,pageNumber,field){
    try{
        const token = localStorage.getItem('authToken');
        console.log("Inside get pagination",token);
        const response = await fetch(`http://localhost:8080/customer/allWithPagination/${pageNumber}/${pageSize}/${field}`,{
            headers: {
                'Authorization':`Bearer ${token}`
            }
        }
        );

        console.log("Inside pagination fucntion",response);
        console.log("Inside pagination fucntion",response.ok);
        if(!response.ok){
            window.location.href = 'index.html';
        }
        let customerData = await response.json();
        console.log(customerData);


        if(customerData.last){
            currentPageNumber = customerList.totalPages-1;
            nextButton.style.backgroundColor = 'lightgrey';
            nextButton.style.display = 'none';
        }
        if(customerData.first){
            currentPageNumber = 0;
            previousButton.style.backgroundColor = 'lightgrey';
        }
        
            customerList = customerData.content;
            tBody.innerHTML = ``;
            for(let i=0;i<customerList.length;i++){
                customer = customerList[i];
                editButtonId = `edit-btn-${customer.id}`;
                deleteButtonId = `delete-btn-${customer.id}`;
                tBody.innerHTML += `
                    <tr id="${customer.id}">
                        <td>${customer.first_name}</td>
                        <td>${customer.last_name}</td>
                        <td>${customer.street}</td>
                        <td>${customer.city}</td>
                        <td>${customer.state}</td>
                        <td>${customer.email}</td>
                        <td>${customer.phone}</td>
                        <td><button onclick= "onEditClick(${customer.id})" class="edit-btn" id="${editButtonId}">Edit</button><button onclick="onDeleteClick(${customer.id})" class="delete-btn" id="${deleteButtonId}">Delete</button> </td>
                    </tr>
            `
            
        }
        
        console.log(customerData.content);
    }catch(error){
        console.error('Error:',error);
    }
}


async function getCustomerList(){
    try{
        const token = localStorage.getItem('authToken');
        console.log("Inside get list",token);
        const response = await fetch(`http://localhost:8080/customer/allWithPagination/${currentPageNumber}/${pageSize}/${field}`,{
            headers: {
                'Authorization':`Bearer ${token}`
            }
        }
        );

        console.log(response);
        console.log(response.ok);
        if(!response.ok){
            window.location.href = 'index.html';
        }
        let customerData = await response.json();

        console.log(customerData);
        if(currentPageNumber<customerData.totalPages){
            customerList = customerData.content;
            tBody.innerHTML =``;
            for(let i=0;i<customerList.length;i++){
                customer = customerList[i];
                editButtonId = `edit-btn-${customer.id}`;
                deleteButtonId = `delete-btn-${customer.id}`;
                tBody.innerHTML += `
                    <tr id="${customer.id}">
                        <td>${customer.first_name}</td>
                        <td>${customer.last_name}</td>
                        <td>${customer.street}</td>
                        <td>${customer.city}</td>
                        <td>${customer.state}</td>
                        <td>${customer.email}</td>
                        <td>${customer.phone}</td>
                        <td><button onclick= "onEditClick(${customer.id})" class="edit-btn" id="${editButtonId}">Edit</button><button onclick="onDeleteClick(${customer.id})" class="delete-btn" id="${deleteButtonId}">Delete</button> </td>
                    </tr>
            `
            
            }
        }
        else{
            nextButton.style.backgroundColor = 'lightgrey';
        }
        console.log(customerData.content);
    }catch(error){
        console.error('Error:',error);
    }
}

getCustomerList();
