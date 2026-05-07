// ============================================================
// Exercício 1.1 — Processamento de Registo de Eventos
// Conceitos: sealed classes, funções de extensão, funções de ordem superior
// ============================================================

// --- 1. Classe selada com subclasses ---
// Uma sealed class restringe as subclasses possíveis ao mesmo ficheiro.
// Isto permite ao compilador verificar exaustividade no 'when'.
sealed class Evento {
    data class Entrada(val nomeUtilizador: String, val instante: Long) : Evento()
    data class Compra(val nomeUtilizador: String, val valor: Double, val instante: Long) : Evento()
    data class Saida(val nomeUtilizador: String, val instante: Long) : Evento()
}

// --- 2. Função de extensão: filtrarPorUtilizador ---
// Adiciona um metodo à List<Evento> sem necessidade de herança.
// Devolve apenas os eventos associados ao utilizador indicado.
fun List<Evento>.filtrarPorUtilizador(nomeUtilizador: String): List<Evento> {
    return this.filter { evento ->
        when (evento) {
            is Evento.Entrada -> evento.nomeUtilizador == nomeUtilizador
            is Evento.Compra -> evento.nomeUtilizador == nomeUtilizador
            is Evento.Saida -> evento.nomeUtilizador == nomeUtilizador
        }
    }
}

// --- 3. Função de extensão: totalGasto ---
// Usa filterIsInstance para filtrar apenas eventos Compra,
// e depois soma todos os valores do utilizador indicado.
fun List<Evento>.totalGasto(nomeUtilizador: String): Double {
    return this
        .filterIsInstance<Evento.Compra>()
        .filter { it.nomeUtilizador == nomeUtilizador }
        .sumOf { it.valor }
}

// --- 4. Função de ordem superior: processarEventos ---
// Recebe uma lista de eventos e uma lambda como parâmetro.
// Aplica a lambda a cada evento da lista.
fun processarEventos(eventos: List<Evento>, handler: (Evento) -> Unit) {
    eventos.forEach { evento -> handler(evento) }
}

// --- 5 & 6. Função principal: testar tudo ---
fun main() {

    val eventos = listOf(
        Evento.Entrada("alice", 1_000),
        Evento.Compra("alice", 49.99, 1_100),
        Evento.Compra("bob", 19.99, 1_200),
        Evento.Entrada("bob", 1_050),
        Evento.Compra("alice", 15.00, 1_300),
        Evento.Saida("alice", 1_400),
        Evento.Saida("bob", 1_500)
    )

    // Processar eventos com lambda
    processarEventos(eventos) { evento ->
        when (evento) {
            is Evento.Entrada ->
                println("[ENTRADA] ${evento.nomeUtilizador} entrou em t=${evento.instante}")

            is Evento.Compra ->
                println("[COMPRA] ${evento.nomeUtilizador} gastou €${evento.valor} em t=${evento.instante}")

            is Evento.Saida ->
                println("[SAÍDA] ${evento.nomeUtilizador} saiu em t=${evento.instante}")
        }
    }

    println()

    // Mostrar total gasto por cada utilizador
    println("Total gasto por alice: €${eventos.totalGasto("alice")}")
    println("Total gasto por bob: €${eventos.totalGasto("bob")}")

    println()

    // Mostrar todos os eventos da alice
    println("Eventos de alice:")
    eventos.filtrarPorUtilizador("alice").forEach { println(it) }
}
