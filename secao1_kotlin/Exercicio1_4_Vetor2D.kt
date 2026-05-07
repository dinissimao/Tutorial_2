// ============================================================
// Exercício 1.4 — Biblioteca de Vetores 2D
// Conceitos: sobrecarga de operadores, Comparable, data class
// ============================================================

import kotlin.math.sqrt

data class Vetor2D(val x: Double, val y: Double) : Comparable<Vetor2D> {

    // Soma componente a componente
    operator fun plus(outro: Vetor2D): Vetor2D {
        return Vetor2D(x + outro.x, y + outro.y)
    }

    // Subtração componente a componente
    operator fun minus(outro: Vetor2D): Vetor2D {
        return Vetor2D(x - outro.x, y - outro.y)
    }

    // Multiplicação por escalar à direita: vetor * 2.0
    operator fun times(escalar: Double): Vetor2D {
        return Vetor2D(x * escalar, y * escalar)
    }

    // Negação do vetor: -vetor
    operator fun unaryMinus(): Vetor2D {
        return Vetor2D(-x, -y)
    }

    // Comparação pela magnitude
    override fun compareTo(outro: Vetor2D): Int {
        return this.magnitude().compareTo(outro.magnitude())
    }

    // Magnitude = raiz quadrada de x² + y²
    fun magnitude(): Double {
        return sqrt(x * x + y * y)
    }

    // Produto escalar
    fun produtoEscalar(outro: Vetor2D): Double {
        return x * outro.x + y * outro.y
    }

    // Vetor normalizado
    fun normalizado(): Vetor2D {
        val mag = magnitude()

        if (mag == 0.0) {
            throw IllegalStateException("Não é possível normalizar o vetor nulo")
        }

        return Vetor2D(x / mag, y / mag)
    }

    // Permite usar vetor[0] e vetor[1]
    operator fun get(indice: Int): Double {
        return when (indice) {
            0 -> x
            1 -> y
            else -> throw IndexOutOfBoundsException("Índice inválido. Usa apenas 0 ou 1.")
        }
    }
}

// Multiplicação por escalar à esquerda: 2.0 * vetor
operator fun Double.times(vetor: Vetor2D): Vetor2D {
    return Vetor2D(this * vetor.x, this * vetor.y)
}

fun main() {

    val a = Vetor2D(3.0, 4.0)
    val b = Vetor2D(1.0, 2.0)

    println("a = $a")
    println("b = $b")

    println("a + b = ${a + b}")
    println("a - b = ${a - b}")
    println("a * 2.0 = ${a * 2.0}")
    println("-a = ${-a}")

    println("|a| = ${a.magnitude()}")
    println("Produto escalar de a e b = ${a.produtoEscalar(b)}")
    println("a normalizado = ${a.normalizado()}")

    println("a[0] = ${a[0]}")
    println("a[1] = ${a[1]}")

    println("a > b = ${a > b}")
    println("a < b = ${a < b}")

    val vetores = listOf(
        Vetor2D(1.0, 0.0),
        Vetor2D(3.0, 4.0),
        Vetor2D(0.0, 2.0)
    )

    println("Maior vetor = ${vetores.max()}")
    println("Menor vetor = ${vetores.min()}")

    println("2.0 * a = ${2.0 * a}")

    // Desestruturação
    // A data class já cria component1() e component2() automaticamente.
    val (px, py) = a
    println("Desestruturação de a: x=$px, y=$py")
}
