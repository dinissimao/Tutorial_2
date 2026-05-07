// ============================================================
// Exercício 1.2 — Cache Genérica em Memória
// Conceitos: genéricos, upper bounds, funções de ordem superior
// ============================================================

// --- Classe Cache<K, V> genérica ---
// K : Any → a chave não pode ser nula
// V : Any → o valor também não pode ser nulo
class Cache<K : Any, V : Any> {

    // Armazenamento interno privado
    private val armazenamento = mutableMapOf<K, V>()

    // Inserir ou substituir valor
    fun inserir(chave: K, valor: V) {
        armazenamento[chave] = valor
    }

    // Obter valor pela chave
    fun obter(chave: K): V? = armazenamento[chave]

    // Remover valor pela chave
    fun remover(chave: K) {
        armazenamento.remove(chave)
    }

    // Tamanho atual da cache
    fun tamanho(): Int = armazenamento.size

    // Se existir devolve.
    // Se não existir calcula, guarda e devolve.
    fun obterOuInserir(chave: K, valorPadrao: () -> V): V {

        val existente = armazenamento[chave]

        if (existente != null) {
            return existente
        }

        val novoValor = valorPadrao()
        armazenamento[chave] = novoValor

        return novoValor
    }

    // Transformar valor existente
    fun transformar(chave: K, acao: (V) -> V): Boolean {

        val existente = armazenamento[chave] ?: return false

        armazenamento[chave] = acao(existente)

        return true
    }

    // Cópia imutável
    fun copia(): Map<K, V> = armazenamento.toMap()

    // Filtrar valores
    fun filtrarValores(predicado: (V) -> Boolean): Map<K, V> {

        return armazenamento
            .filter { (_, valor) -> predicado(valor) }
            .toMap()
    }
}

fun main() {

    // ---------------------------------
    // Cache<String, Int> palavras
    // ---------------------------------
    println("--- Cache de frequência de palavras ---")

    val cachePalavras = Cache<String, Int>()

    cachePalavras.inserir("kotlin", 1)
    cachePalavras.inserir("scala", 1)
    cachePalavras.inserir("haskell", 1)

    println("Tamanho: ${cachePalavras.tamanho()}")
    println("Frequência de kotlin: ${cachePalavras.obter("kotlin")}")

    println("obterOuInserir kotlin: ${
        cachePalavras.obterOuInserir("kotlin") { 0 }
    }")

    println("obterOuInserir java: ${
        cachePalavras.obterOuInserir("java") { 0 }
    }")

    println("Tamanho após obterOuInserir: ${cachePalavras.tamanho()}")

    println("Transformar kotlin (+1): ${
        cachePalavras.transformar("kotlin") { it + 1 }
    }")

    println("Transformar cobol (+1): ${
        cachePalavras.transformar("cobol") { it + 1 }
    }")

    println("Cópia: ${cachePalavras.copia()}")

    println("Palavras com valor > 0: ${
        cachePalavras.filtrarValores { it > 0 }
    }")

    println()

    // ---------------------------------
    // Cache<Int, String> identificadores
    // ---------------------------------
    println("--- Cache de identificadores ---")

    val cacheIds = Cache<Int, String>()

    cacheIds.inserir(1, "Alice")
    cacheIds.inserir(2, "Bob")

    println("Id 1 -> ${cacheIds.obter(1)}")
    println("Id 2 -> ${cacheIds.obter(2)}")

    cacheIds.remover(1)

    println("Após remover id 1, tamanho: ${cacheIds.tamanho()}")
    println("Id 1 após remover -> ${cacheIds.obter(1)}")
}
