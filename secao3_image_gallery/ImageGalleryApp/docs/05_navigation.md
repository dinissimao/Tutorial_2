# Navegação

## Fluxo de navegação

```
MainActivity
     ↓ (carregar numa imagem)
ImageDetailsActivity
     ↓ (botão voltar)
MainActivity
```

## Detalhes
- A navegação de MainActivity para ImageDetailsActivity é feita passando o URL e o id da imagem via Intent
- O botão de voltar na toolbar do ImageDetailsActivity regressa à MainActivity
- A barra de favoritos está acessível na MainActivity