# Visão Geral da Aplicação

## Propósito
O ImageGalleryApp é uma aplicação Android que obtém imagens de cães de raças aleatórias através da Dog CEO API e apresenta-as numa galeria interactiva.

## Utilizadores alvo
Utilizadores que gostam de explorar imagens de cães e guardar as suas favoritas.

## Como o sistema funciona
1. A app arranca e obtém 20 imagens aleatórias da Dog CEO API
2. As imagens são apresentadas numa grelha (RecyclerView)
3. O utilizador pode carregar numa imagem para ver os detalhes
4. O utilizador pode marcar imagens como favoritas (máximo 5)
5. A app mantém uma cache de até 50 imagens para acesso offline
6. O utilizador pode actualizar a galeria com o botão de refresh