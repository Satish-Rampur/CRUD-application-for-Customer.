const mainContainer = document.getElementById('main-container');
const userNameField = document.getElementById('username');
const passwordField = document.getElementById('password');
const loginButton = document.getElementById('login-btn');


checkTokenAndRedirect();

loginButton.addEventListener('click',(event)=>{
    event.preventDefault();
    let userName = userNameField.value;
    let password = passwordField.value; 
    console.log(userName,password);
    getToken(userName,password);
})


async function getToken(userName,password){
    try {
        const response = await fetch('http://localhost:8080/admin/generateToken', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                userName: userName,
                password: password
            })
        });

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        const token = await response.text();
        
        console.log('Token:', token); 
        localStorage.setItem('authToken', token);
        window.location.href = 'customerList.html';
    } catch (error) {
        console.error('Error:', error);
    }
}

async function checkTokenAndRedirect(){
    try{
        const token = localStorage.getItem('authToken');
        if(token){
           const response = await fetch('http://localhost:8080/customer/all',
            {
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            }
           );
           if(response.ok){
            window.location.href = 'customerList.html';
           }
        }
    }catch(error){
        console.error('Error:',error);
    }
}

