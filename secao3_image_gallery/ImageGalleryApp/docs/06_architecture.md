# Arquitectura

## Padrão: MVVM (Model-View-ViewModel)

```
UI (View)
    ↓ observa LiveData
ViewModel
    ↓ chama
Repository
    ↓ obtém dados
API Service (Dog CEO API)
```

## Camadas

### View
- `MainActivity` — ecrã principal com galeria
- `ImageDetailsActivity` — ecrã de detalhes
- Adaptadores: `ImageAdapter`, `FavoritesAdapter`

### ViewModel
- `GalleryViewModel` — gere o estado da galeria, favoritos e cache

### Repository
- `ImageRepository` — responsável por obter imagens da API e gerir a cache

### Model
- `ImageItem` — modelo de dados de uma imagem
- `ApiResponse` — modelo da resposta da API