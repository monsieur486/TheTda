let stompClient = null;
const ctx = document.getElementById('myChart');

function connect() {
    const socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function () {
        document.getElementById('status').innerText = '🟢';
        stompClient.subscribe('/topic/state', function (message) {
            const state = JSON.parse(message.body);
            updateState(state);
        });
        stompClient.send("/app/state.get", {}, {});
    }, function (error) {
        document.getElementById('status').innerText = '🔴 ' + error;
    });
}

function updateState(state) {
    if(state.level === 100) recuperationData();
}

function recuperationData(){
    console.log("API:" + apiUrl);
    axios.get(apiUrl)
        .then(response => {
            const data = response.data;
            console.log(data);
        })
        .catch(error => {
            console.error("Erreur lors de la récupération des information :", error);
            document.getElementById('error').innerHTML = "<p class='text-danger'>Impossible de charger les informations.</p>";
        });
}

connect();