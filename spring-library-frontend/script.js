document.addEventListener("DOMContentLoaded", function () {
  // Dichiaro costanti
  const apiUrl = 'http://localhost:8080/api/v1/photos';
  const root = document.getElementById("root");

  // Funzione che renderizza le categorie
  const renderCategories = (categories) => {
    let content;
    if (categories.length === 0) {
      content = 'No categories';
    } else {
      content = '<ul class="list-unstyled">';
      categories.forEach((cat) => {
        content += `<li>${cat.name}</li>`;
      });
      content += '</ul>';
    }
    return content;
  };

  // Funzione che renderizza una singola foto
  const renderPhoto = (element) => {
    return `<div class="card shadow h-100 d-flex flex-column">
      <div class="card-body" style="max-width: 100%;">
          <h5 class="card-title">${element.title}</h5>
          <p class="card-text">${element.description}</p>
      </div>
      <div class="photo-wrapper flex-grow-1" style="height: 700px;">
          <img src="${element.url}" class="card-img-top img-fluid h-100" alt="${element.title}">
      </div>
      <div class="card-footer" style="height: 100px; overflow: hidden;">${renderCategories(element.categories)}</div>
    </div>`;
  };

  // Funzione che renderizza la lista di foto
  const renderPhotoList = (data) => {
    let content;
    if (data.length > 0) {
      content = '<div class="row">';
      data.forEach((element) => {
        content += '<div class="col-4 mb-4">';
        content += renderPhoto(element);
        content += '</div>';
      });
      content += '</div>';
    } else {
      content = '<div class="alert alert-info">The list is empty</div>';
    }
    // Sostituisco il contenuto di root con il mio content
    root.innerHTML = content;
  };

  // Dichiaro la funzione per ottenere le foto
  const getPhotosForApi = async () => {
    try {
      const response = await axios.get(apiUrl);
      renderPhotoList(response.data);
    } catch (error) {
      console.error(error);
    }
  };

  // Aggiungi la barra di ricerca
  const searchPhotos = async (searchTerm) => {
    try {
      const response = await axios.get(`${apiUrl}?search=${searchTerm}`);
      renderPhotoList(response.data);
    } catch (error) {
      console.error(error);
    }
  };

  // Funzione per inviare un messaggio
  const sendMessage = async (message) => {
    try {
      const response = await axios.post('http://localhost:8080/api/v1/messages', message);
      console.log(response.data); // Puoi gestire la risposta qui
      return response.data; // Restituisci la risposta per gestire eventuali informazioni aggiuntive
    } catch (error) {
      console.error(error);
      throw error; // Rilancia l'errore per gestire eventuali errori aggiuntivi
    }
  };

  // Chiamata iniziale per ottenere tutte le foto
  getPhotosForApi();

  // Evento di ricerca
  const searchForm = document.getElementById("search-form");
  searchForm.addEventListener("submit", (event) => {
    event.preventDefault();
    const searchTerm = document.getElementById("search-input").value;
    searchPhotos(searchTerm);
  });

  const contactForm = document.getElementById("contact-form");
  contactForm.addEventListener("submit", async (event) => {
    event.preventDefault();
    const emailInput = document.getElementById("email");
    const messageInput = document.getElementById("message");

    if (contactForm.checkValidity()) {
      try {
        const message = {
          senderEmail: emailInput.value,
          body: messageInput.value
        };
        // Invia il messaggio
        await sendMessage(message);

        // Mostra un feedback di successo
        alert("Message sent successfully!");

        // Svuota il form
        contactForm.reset();
      } catch (error) {
        // Gestisci eventuali errori aggiuntivi
        console.error("Failed to send message:", error);
      }
    } else {
      // Se il form non è valido, mostra i messaggi di errore
      emailInput.classList.add("is-invalid");
      messageInput.classList.add("is-invalid");
    }
  });

  // Rimuovi la classe "is-invalid" quando si modifica l'input
  ["email", "message"].forEach((inputId) => {
    const input = document.getElementById(inputId);
    input.addEventListener("input", () => {
      if (input.checkValidity()) {
        input.classList.remove("is-invalid");
      }
    });
  });
});
