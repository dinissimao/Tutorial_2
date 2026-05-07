# Modelo de Dados

## ImageItem
Representa uma imagem obtida da API.

```
ImageItem
  id: String        — identificador único (gerado localmente)
  url: String       — URL da imagem
  isFavorite: Boolean — se está marcada como favorita
```

## ApiResponse
Representa a resposta da Dog CEO API.

```
ApiResponse
  message: List<String>  — lista de URLs das imagens
  status: String         — "success" ou "error"
```

## AppState
Estado global da aplicação gerido pelo ViewModel.

```
AppState
  images: List<ImageItem>      — imagens actuais na galeria
  favorites: List<ImageItem>   — lista de favoritos (máx. 5)
  isLoading: Boolean           — true enquanto carrega
  errorMessage: String?        — mensagem de erro ou null
```