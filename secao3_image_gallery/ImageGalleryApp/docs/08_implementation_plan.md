# Plano de Implementação

## Passo 1
Configurar o projecto Android com Kotlin e XML Views.
Adicionar as dependências necessárias ao build.gradle:
- Retrofit (chamadas à API)
- Glide (carregamento de imagens)
- ViewModel e LiveData (MVVM)
- Coroutines (operações assíncronas)

## Passo 2
Criar o modelo de dados:
- Classe `ImageItem`
- Classe `ApiResponse`

## Passo 3
Criar o serviço da API com Retrofit:
- Interface `DogApiService` com o endpoint de imagens aleatórias

## Passo 4
Criar o `ImageRepository`:
- Método para obter imagens da API
- Gestão da cache (máximo 50 imagens)
- Suporte a acesso offline

## Passo 5
Criar o `GalleryViewModel`:
- LiveData para imagens, favoritos, estado de carregamento e erros
- Lógica de favoritos (fila FIFO, máximo 5)
- Método para actualizar a galeria

## Passo 6
Criar o layout `activity_main.xml`:
- Toolbar
- RecyclerView em grelha de 2 colunas
- ProgressBar
- Botão de refresh
- Barra de favoritos na parte inferior

## Passo 7
Criar o `ImageAdapter` para o RecyclerView principal.

## Passo 8
Criar o `FavoritesAdapter` para a barra de favoritos.

## Passo 9
Implementar a `MainActivity`:
- Observar o ViewModel
- Configurar os adaptadores
- Tratar cliques nas imagens e no botão de refresh

## Passo 10
Criar o layout `activity_image_details.xml`:
- Imagem em tamanho grande
- Informação da imagem
- Botão de favorito

## Passo 11
Implementar a `ImageDetailsActivity`:
- Receber dados via Intent
- Mostrar detalhes da imagem
- Adicionar/remover dos favoritos

## Passo 12
Testar toda a aplicação:
- Verificar carregamento de imagens
- Verificar sistema de favoritos
- Verificar cache e acesso offline
- Verificar tratamento de erros