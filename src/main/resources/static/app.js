let stompClient = null;
const topicName = '/topic/message';




const setConnected = () => {
    let successDot = document.getElementById('success-connection');
    successDot.removeAttribute('hidden');
}

const connect = () => {
    stompClient = Stomp.over(new SockJS('/ws'));
    stompClient.connect({}, () => {
        setConnected();
        stompClient.subscribe(topicName, (message) => showMessage(JSON.parse(message.body) ));
    });
}


const showMessage = (message) => {
    const container = document.getElementById("container-messages");
    let node = buildMessageCard(message);
    container.appendChild(node);
    window.scrollTo(0, document.body.scrollHeight);
}

const buildMessageCard = (message) =>{
    let character = message.Character;
    let html = "<div class=\"card mb-2\" style=\"width: 35rem;\">\n" +
        "        <div class=\"card-header\">\n" +
        "            <h4>\n" +
        "                Event type: <strong>" +
        message.Event +
        "</strong>\n" +
        "            </h4>\n" +
        "            Time: <strong>" +
        new Date() +
        "</strong>\n" +
        "        </div>\n" +
        "        <div class=\"card-body\">\n" +
        "            <h5 class=\"card-title\">Id: " +
        character.id +
        "</h5>\n" +
        "            <p class=\"card-text\">\n" +
        "            <ul>\n" +
        "                <li>Name: " +
        character.name +
        "</li>\n" +
        "                <li>Gender: " +
        character.gender +
        "</li>\n" +
        "                <li>Birth year: " +
        character.birth_year +
        "</li>\n" +
        "                <li>Homeworld: " +
        character.homeworld +
        "</li>\n" +
        "            </ul>\n" +
        "            </p>\n" +
        "        </div>\n" +
        "    </div>";
    return makeNodeFromTemplate(html);
}



const makeNodeFromTemplate = (rawHtml) => {
    const template = document.createElement("template");
    template.innerHTML = rawHtml;
    const nNodes = template.content.childNodes.length;
    if (nNodes !== 1) {
        throw new Error("error creating answer for " + rawHtml.substring(0, 60) + "...");
    }
    return template.content.firstChild;
}