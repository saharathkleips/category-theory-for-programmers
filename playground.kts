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