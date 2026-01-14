let stompClient = null;
const statusIcon = document.getElementById('ws-icon');

function setWsConnected(on) {
    if (statusIcon) {
        statusIcon.textContent = on ? '🔗' : '⛓️‍💥';
        statusIcon.title = on ? 'Connecté' : 'Déconnecté';
    }
}

function connect() {
    const socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.debug = () => {
    };

    stompClient.connect({}, function (frame) {
        console.log('STOMP connected:', frame);
        setWsConnected(true);
        stompClient.subscribe('/topic/messages', function (msg) {
            console.log('Message reçu:', msg.body);
        });
    }, function (error) {
        console.error('STOMP error:', error);
        setWsConnected(false);
        setTimeout(connect, 2000);
    });
}

document.addEventListener('DOMContentLoaded', function () {
    connect();
});