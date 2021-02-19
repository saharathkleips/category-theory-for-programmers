import kotlin.random.Random

// Identity
fun <A> identity(a: A): A = a
val identity: (String) -> String = ::identity

// Composition
infix fun <A, B, C> ((B) -> C).compose(f: (A) -> B): (A) -> C = { this(f(it)) }

val f: (Int) -> String = { it.toString() }
val g: (String) -> Double = { it.toDouble() }
val h: (Double) -> Float = { it.toFloat() }
val g_after_f: (Int) -> Double = g compose f
val h_after_g_after_f: (Int) -> Float = h compose g compose f

// Respect Identity
val f1: (String) -> String = { it }
val f2: (String) -> String = f1 compose identity
val f3: (String) -> String = identity compose f1

// Memoize
class Memoize<in A, out B>(val f: (A) -> B) : (A) -> B {
    private val memoized = mutableMapOf<A, B>()
    override fun invoke(a: A): B = memoized.getOrPut(a) { f(a) }
}

fun <A, B> ((A) -> B).memoize(): (A) -> B = Memoize(this)
@JvmName("memoizeFn") fun <A, B> memoize(f: (A) -> B): (A) -> B = Memoize(f)

val bigMath = memoize { a: Int -> Thread.sleep(5000); a * 2 }
val bigMath2 = { a: Int -> Thread.sleep(5000); a * 2 }.memoize()

//println(bigMath(2))
//println(bigMath(2))

// Memoize Random Seed
val randomSeed: (Int) -> Int = { (0..100).random(Random(it)) }
val randomSeedMemoized = randomSeed.memoize()

println(randomSeedMemoized(42))
println(randomSeedMemoized(42))

// Boolean Functions
val identityBoolean: (Boolean) -> Boolean = { it }
val inverseBoolean: (Boolean) -> Boolean = { !it }
val alwaysTrue: (Boolean) -> Boolean = { true }
val alwaysFalse: (Boolean) -> Boolean = { false }
