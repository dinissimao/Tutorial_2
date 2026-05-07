# Tutorial 2 - Cool Weather Application & Image Gallery App

Course: Computação Móvel — Mobile Computing (CM)
Student: Dinis António Figueiredo Simão
Date: Abril 2026
Repository URL: https://github.com/dinissimao/Tutorial_2

---

## 1. Introdução

Este tutorial tem como objetivo desenvolver competências em Kotlin e Android através de três componentes principais. A primeira componente consiste numa série de exercícios em Kotlin que exploram conceitos avançados da linguagem, como sealed classes, genéricos, funções de ordem superior e sobrecarga de operadores. A segunda componente consiste no desenvolvimento de uma aplicação Android chamada Cool Weather App, que apresenta informação meteorológica em tempo real obtida através da API Open-Meteo, com suporte a múltiplos idiomas, temas Dia/Noite e o padrão de arquitetura MVVM. A terceira componente consiste no desenvolvimento de uma aplicação de galeria de imagens chamada ImageGalleryApp, construída com recurso a desenvolvimento assistido por IA através do AntiGravity IDE.

---

## 2. Visão Geral do Sistema

### Secção 1 — Exercícios Kotlin
Quatro exercícios independentes desenvolvidos em Kotlin puro, sem recurso a IA:
- **Exercício 1.1** — Processamento de registos de eventos com sealed classes e funções de extensão
- **Exercício 1.2** — Cache genérica em memória com suporte a getOrPut, transform e snapshot
- **Exercício 1.3** — Pipeline de dados configurável com funções de ordem superior e DSL
- **Exercício 1.4** — Biblioteca de vetores 2D com sobrecarga de operadores e Comparable

### Secção 2 — Cool Weather App
Aplicação Android que apresenta dados meteorológicos em tempo real:
- Dados obtidos da API Open-Meteo (pressão, vento, temperatura, hora)
- Detecção automática de localização via GPS
- Temas Dia/Noite com fundos diferentes
- Layouts adaptados para portrait, landscape e tablet
- Suporte a português e inglês
- Arquitectura MVVM com LiveData e Coroutines

### Secção 3 — ImageGalleryApp
Aplicação Android de galeria de imagens desenvolvida com IA:
- Imagens obtidas da Dog CEO API
- Galeria em grelha com RecyclerView
- Sistema de favoritos (máximo 5, fila FIFO)
- Cache de até 50 imagens com suporte offline
- Ecrã de detalhes por imagem
- Arquitectura MVVM com Repository pattern

---

## 3. Arquitectura e Design

### Cool Weather App
A aplicação segue o padrão MVVM:
```
View (MainActivity)
    ↓ observa LiveData
ViewModel (WeatherViewModel)
    ↓ chama
Repository (WeatherRepository)
    ↓ obtém dados
API Open-Meteo
```

Estrutura de pastas relevante:
```
app/src/main/
 ├── java/com/example/coolweatherapp/
 │    ├── MainActivity.kt
 │    ├── WeatherViewModel.kt
 │    ├── WeatherRepository.kt
 │    └── WeatherData.kt
 └── res/
      ├── layout/activity_main.xml
      ├── layout-land/activity_main.xml
      ├── layout-sw600dp/activity_main.xml
      ├── drawable/ (fundos e ícones meteorológicos)
      ├── values/
      │    ├── colors.xml
      │    ├── strings.xml (português)
      │    └── themes.xml
      └── values-en/
           └── strings.xml (inglês)
```

### ImageGalleryApp
A aplicação segue o mesmo padrão MVVM com Repository:
```
View (MainActivity / ImageDetailsActivity)
    ↓ observa LiveData
ViewModel (GalleryViewModel)
    ↓ chama
Repository (ImageRepository)
    ↓ obtém dados
Dog CEO API
```

---

## 4. Implementação

### Exercícios Kotlin

**Exercício 1.1 — Event Log Processing**
Implementação de uma sealed class `Event` com subclasses `Login`, `Purchase` e `Logout`. Funções de extensão `filterByUser` e `totalSpent` adicionadas a `List<Event>`. Função de ordem superior `processEvents` que aplica uma lambda a cada evento.

**Exercício 1.2 — Cache Genérica**
Classe `Cache<K : Any, V : Any>` com armazenamento interno em `MutableMap`. Implementação dos métodos `put`, `get`, `evict`, `size`, `getOrPut`, `transform`, `snapshot` e `filterValues`.

**Exercício 1.3 — Pipeline de Dados**
Classe `Pipeline` com lista ordenada de etapas. Função DSL `buildPipeline` com receiver lambda. Método `execute` usa `fold` para aplicar as transformações sequencialmente.

**Exercício 1.4 — Biblioteca Vec2**
Data class `Vec2` que implementa `Comparable<Vec2>`. Sobrecarga dos operadores `+`, `-`, `*`, `-` unário, `[]` e `compareTo`. Função de extensão em `Double` para multiplicação escalar à esquerda.

### Cool Weather App

A chamada à API é feita através do `WeatherRepository` usando `URL.openStream()` e desserialização com Gson. O `WeatherViewModel` usa coroutines com `Dispatchers.IO` para executar a chamada em background e expõe os resultados via `LiveData`. A `MainActivity` observa o `LiveData` e atualiza a interface. A detecção de GPS usa `FusedLocationProviderClient` com pedido de permissão em runtime.

### ImageGalleryApp

A aplicação usa Retrofit para comunicar com a Dog CEO API e Glide para carregar as imagens. O sistema de favoritos é implementado como uma fila FIFO com máximo de 5 elementos. A cache de imagens suporta até 50 itens e permite acesso offline.

---

## 5. Testes e Validação

### Cool Weather App
- Testada no emulador Google Pixel (Medium Phone API 36.1)
- Verificado o carregamento de dados meteorológicos para coordenadas de Lisboa e coordenadas automáticas via GPS
- Verificada a mudança de tema entre Dia e Noite
- Verificada a mudança de idioma entre português e inglês
- Testados os layouts portrait, landscape e tablet

### ImageGalleryApp
- Compilação verificada com `assembleDebug` via Gradle — BUILD SUCCESSFUL
- Testada no emulador Android
- Verificado o carregamento de imagens da Dog CEO API
- Verificado o funcionamento do botão de refresh

### Exercícios Kotlin
- Cada exercício foi testado com os dados de amostra fornecidos no enunciado
- Os resultados foram comparados com o output esperado indicado no tutorial

---

## 6. Instruções de Utilização

### Requisitos
- Android Studio (versão recente)
- Android SDK API 24 ou superior
- Ligação à Internet (para obter dados meteorológicos e imagens)

### Cool Weather App
1. Abre o projecto `CoolWeatherApp` no Android Studio
2. Aguarda a sincronização do Gradle
3. Corre a app num emulador ou dispositivo físico
4. A app pede permissão de localização — aceitar para usar GPS automático
5. Os dados meteorológicos carregam automaticamente para a localização actual
6. Para consultar outra localização, introduz as coordenadas e prime ACTUALIZAR

### ImageGalleryApp
1. Abre o projecto `ImageGalleryApp` no Android Studio
2. Aguarda a sincronização do Gradle
3. Corre a app num emulador ou dispositivo físico
4. As imagens carregam automaticamente ao arrancar
5. Prime o botão de refresh para obter novas imagens
6. Carrega numa imagem para ver os detalhes
7. Adiciona imagens aos favoritos através do botão na vista de detalhes

### Exercícios Kotlin
1. Abre cada ficheiro `.kt` no IntelliJ IDEA ou Android Studio
2. Corre a função `main()` de cada ficheiro
3. Verifica o output na consola

---

# Secções de Engenharia de Software Autónoma

## 7. Estratégia de Prompting

Para a Secção 3 (ImageGalleryApp), foi adoptada uma abordagem planning-first, onde toda a especificação foi documentada em ficheiros Markdown antes de qualquer geração de código. Os prompts foram estruturados de forma a guiar o agente passo a passo, seguindo o plano de implementação definido em `docs/08_implementation_plan.md`.

Exemplos de prompts utilizados:

**Prompt inicial:**
```
Por favor lê todos os ficheiros de documentação dentro da pasta /docs antes 
de gerares qualquer código. Começa pelo Passo 1 do plano de implementação 
em docs/08_implementation_plan.md. Configura o projecto Android e adiciona 
todas as dependências necessárias ao build.gradle.kts para: Retrofit, Glide, 
ViewModel, LiveData e Coroutines. Segue a arquitetura definida em 
docs/06_architecture.md e usa apenas Kotlin com XML Views.
```

**Prompts de continuação:**
```
Aceite. Avança para o Passo [N] e [descrição do passo].
```

---

## 8. Fluxo de Trabalho com o Agente Autónomo

O fluxo de trabalho seguido com o AntiGravity foi o seguinte:

1. **Especificação** — Criação de 11 ficheiros Markdown com toda a documentação do projecto (visão geral, funcionalidades, ecrãs, modelo de dados, navegação, arquitetura, API, plano de implementação)
2. **Definição de regras** — Criação do ficheiro `agents.md` com as regras de comportamento do agente
3. **Geração de código** — O agente leu a documentação e gerou o código passo a passo, pedindo aprovação antes de cada alteração
4. **Revisão** — Cada passo foi revisto antes de clicar em Accept All
5. **Compilação** — O agente executou `assembleDebug` para verificar que o projecto compila sem erros
6. **Testes** — A app foi testada no Android Studio após cada conjunto de alterações

---

## 9. Verificação dos Artefactos Gerados por IA

O código gerado pelo AntiGravity foi verificado das seguintes formas:

- **Compilação** — O agente executou `assembleDebug` com resultado BUILD SUCCESSFUL, confirmando que o código não tem erros de sintaxe nem dependências em falta
- **Revisão manual** — Cada ficheiro gerado foi aberto e lido antes de aceitar as alterações
- **Testes funcionais** — A app foi corrida no emulador e verificou-se que as imagens carregam, o refresh funciona e a navegação está correcta
- **Consistência com a documentação** — Verificou-se que o código gerado respeita a arquitetura MVVM definida em `docs/06_architecture.md`

---

## 10. Contribuição Humana vs IA

| Componente | Desenvolvimento Humano | Desenvolvimento com IA |
|---|---|---|
| Secção 1 — Exercícios Kotlin | 100% | 0% |
| Secção 2 — Cool Weather App | 100% | 0% |
| Secção 3 — Documentação Markdown | 100% | 0% |
| Secção 3 — Código da ImageGalleryApp | 0% | 100% (AntiGravity) |
| Secção 3 — Revisão e testes | 100% | 0% |

---

## 11. Uso Ético e Responsável da IA

O uso do AntiGravity foi limitado à Secção 3 (MIP-2), conforme permitido pelo enunciado. Em todas as outras secções (exercícios Kotlin e Cool Weather App), não foi utilizada qualquer ferramenta de IA, respeitando a política de integridade académica definida no tutorial.

O código gerado pelo AntiGravity foi sempre revisto antes de ser aceite. O aluno mantém a responsabilidade pela compreensão e validação de todo o código presente no repositório.

Não foram identificados outputs inadequados, enviesados ou incorrectos por parte da ferramenta de IA utilizada.

---

# Processo de Desenvolvimento

## 12. Controlo de Versão e Histórico de Commits

O repositório foi gerido com Git ao longo de todo o desenvolvimento. Os commits foram feitos de forma incremental, reflectindo a progressão do trabalho:

- Commit inicial com a estrutura do projecto
- Commits separados para cada exercício Kotlin
- Commits ao longo do desenvolvimento da Cool Weather App (layout, temas, API, GPS, MVVM)
- Commits para a documentação Markdown da ImageGalleryApp
- Commits para o código gerado pela IA em cada passo do plano de implementação
- Commit final com o relatório

---

## 13. Dificuldades e Lições Aprendidas

- **Temas Android** — A aplicação do tema via `setTheme()` nem sempre atualizava o fundo visualmente. Foi necessário aplicar o fundo directamente ao `ConstraintLayout` via `setBackgroundResource()` para garantir a mudança.
- **Package name** — Inicialmente foi usado um package name incorrecto nos ficheiros Kotlin gerados, o que causou erros de compilação. Lição: verificar sempre o package name antes de copiar código.
- **AntiGravity e ficheiros vazios** — O agente não conseguia ler os ficheiros Markdown porque não estavam guardados. Lição: sempre guardar os ficheiros com Ctrl+S antes de invocar o agente.
- **GPS no emulador** — O emulador usa coordenadas simuladas de São Francisco por defeito, não de Lisboa. Isto é comportamento esperado e não um erro da aplicação.
- **MVVM** — A separação entre View, ViewModel e Repository tornou o código mais organizado e fácil de manter, mas exigiu uma compreensão clara das responsabilidades de cada camada.

---

## 14. Melhorias Futuras

- **Cool Weather App** — Calcular automaticamente se é dia ou noite com base no nascer e pôr do sol devolvidos pela API, em vez de usar uma variável booleana manual
- **Cool Weather App** — Adicionar previsão para os próximos dias
- **Cool Weather App** — Adicionar um mapa para seleccionar a localização graficamente
- **ImageGalleryApp** — Adicionar suporte a múltiplas raças de cães com filtros
- **ImageGalleryApp** — Implementar pesquisa por raça
- **Geral** — Adicionar testes unitários e de instrumentação

---

## 15. Declaração de Uso de IA (Obrigatório)

| Ferramenta | Onde foi utilizada | Como foi utilizada |
|---|---|---|
| AntiGravity IDE (Gemini 3.1 Pro) | Secção 3 — ImageGalleryApp | Geração de código Kotlin e XML a partir de especificações em Markdown, seguindo um plano de implementação de 12 passos |
| Claude (Anthropic) | Relatório README.md | Apoio na redação e estruturação do relatório, esclarecimento de conceitos e revisão de conteúdo |

O aluno confirma que:
- O AntiGravity foi utilizado **apenas** na Secção 3 (MIP-2) para geração de código, conforme permitido pelo enunciado
- O Claude foi utilizado como apoio na redação e estruturação deste relatório, conforme permitido pelo enunciado
- Nas Secções 1 e 2, **não foi utilizada qualquer ferramenta de IA para geração de código**
- Todo o código gerado pela IA foi revisto pelo aluno
- O aluno mantém a responsabilidade pela totalidade do conteúdo submetido
