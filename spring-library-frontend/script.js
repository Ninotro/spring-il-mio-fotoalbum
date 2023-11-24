// dichiaro costanti
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
const getPhotos = async () => {
  try {
    const response = await axios.get(apiUrl);
    renderPhotoList(response.data);
  } catch (error) {
    console.log(error);
  }
};

// Aggiungi la barra di ricerca
const searchPhotos = async (searchTerm) => {
  try {
    const response = await axios.get(`${apiUrl}?search=${searchTerm}`);
    renderPhotoList(response.data);
  } catch (error) {
    console.log(error);
  }
};

// Chiamata iniziale per ottenere tutte le foto
getPhotos();

// Evento di ricerca
const searchForm = document.getElementById("search-form");
searchForm.addEventListener("submit", (event) => {
  event.preventDefault();
  const searchTerm = document.getElementById("search-input").value;
  searchPhotos(searchTerm);
});
