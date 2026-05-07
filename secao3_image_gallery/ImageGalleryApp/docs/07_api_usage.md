# Utilização da API

## API: Dog CEO
- **Base URL**: `https://dog.ceo/api`
- **Documentação**: https://dog.ceo/dog-api

## Endpoint utilizado

### Obter imagens aleatórias
```
GET https://dog.ceo/api/breeds/image/random/20
```

### Exemplo de resposta
```json
{
  "message": [
    "https://images.dog.ceo/breeds/hound-afghan/n02088094_1003.jpg",
    "https://images.dog.ceo/breeds/beagle/n02088364_1003.jpg"
  ],
  "status": "success"
}
```

## Notas
- Não requer autenticação (API pública e gratuita)
- O número no final do endpoint define quantas imagens são devolvidas (usamos 20)
- Em caso de erro, o campo `status` devolve `"error"`
- As imagens devem ser carregadas com a biblioteca Glide