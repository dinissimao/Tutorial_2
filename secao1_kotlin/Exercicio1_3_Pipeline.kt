// ============================================================
// Exercício 1.3 — Pipeline de Dados Configurável
// Conceitos: funções de ordem superior, lambdas, composição de funções
// ============================================================

class Pipeline {

    private val etapas = mutableListOf<Pair<String, (List<String>) -> List<String>>>()

    // Adicionar uma etapa ao pipeline
    fun adicionarEtapa(nome: String, transformacao: (List<String>) -> List<String>) {
        etapas.add(nome to transformacao)
    }

    // Executar todas as etapas por ordem
    fun executar(entrada: List<String>): List<String> {
        return etapas.fold(entrada) { atual, (_, transformacao) ->
            transformacao(atual)
        }
    }

    // Mostrar as etapas do pipeline
    fun descrever() {
        println("Etapas do pipeline:")

        etapas.forEachIndexed { indice, (nome, _) ->
            println("${indice + 1}. $nome")
        }
    }

    // Combinar duas etapas existentes numa só
    fun compor(nome1: String, nome2: String) {

        val etapa1 = etapas.find { it.first == nome1 }?.second
        val etapa2 = etapas.find { it.first == nome2 }?.second

        if (etapa1 != null && etapa2 != null) {
            etapas.removeAll { it.first == nome1 || it.first == nome2 }

            adicionarEtapa("$nome1 + $nome2") { entrada ->
                etapa2(etapa1(entrada))
            }
        }
    }

    // Executar a mesma entrada em dois pipelines diferentes
    fun dividir(outro: Pipeline, entrada: List<String>): Pair<List<String>, List<String>> {
        return this.executar(entrada) to outro.executar(entrada)
    }
}

// Função para construir o pipeline
fun construirPipeline(bloco: Pipeline.() -> Unit): Pipeline {
    val pipeline = Pipeline()
    pipeline.bloco()
    return pipeline
}

fun main() {

    val registos = listOf(
        "  INFO: servidor iniciado  ",
        "  ERRO: disco cheio  ",
        "  DEBUG: a verificar configuração  ",
        "  ERRO: memória esgotada  ",
        "  INFO: pedido recebido  ",
        "  ERRO: ligação expirada  "
    )

    val pipeline = construirPipeline {

        adicionarEtapa("Remover espaços") { linhas ->
            linhas.map { it.trim() }
        }

        adicionarEtapa("Filtrar erros") { linhas ->
            linhas.filter { it.contains("ERRO") }
        }

        adicionarEtapa("Maiúsculas") { linhas ->
            linhas.map { it.uppercase() }
        }

        adicionarEtapa("Adicionar índice") { linhas ->
            linhas.mapIndexed { indice, linha ->
                "${indice + 1}. $linha"
            }
        }
    }

    pipeline.descrever()

    println()

    val resultado = pipeline.executar(registos)

    println("Resultado:")

    resultado.forEach {
        println(it)
    }
}
