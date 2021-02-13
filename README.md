# [Category Theory for Programmers](https://github.com/hmemcpy/milewski-ctfp-pdf)
Notes from book club.

## Category: The Essence of Composition

- `Morphisms` can be thought of as "arrows" or functions
    - `f = A => B` and `g = B => C`, therefore you can write a function that composes `f` and `g` to get `A => C`
    - Composition in math is written right to left as `𝑔 ∘ 𝑓` or "g after f"
    - ```kotlin
      val f: (a: A) -> B
      val g: (b: B) -> C
      val g_after_f: (a: A) -> C = g(f(a))
      ```
    - ```haskell
      f :: A -> B
      g :: B -> C
      g.f  
      ```
        - `::` means "has type of"
        - `.` means composition
- Composition is associative, so with three morphisms `f`, `g`, and `h`: `(h.g).f = h.(g.f) = h.g.f`
- Every object has a unit arrow, e.g. a function that starts with A and ends with A
    - This is called "identity on A": `𝑓 ∘ id𝐴 = 𝑓` and `id𝐵 ∘ 𝑓 = 𝑓`
    - This also means that the identity arrow is same for every type
    - ```kotlin
      fun <T> id(t: T): T = t
      ```
    - ```haskell
      id :: a -> a
      id x = x
    
      f.id == f 
      id.f == f
      ```
- Higher order functions are the algebra of functions
- "We often describe some piece of code as elegant or beautiful, but what we really mean is that it’s easy to process by
  our limited human minds"
    - "The idea is that, once a chunk is implemented, we can forget about the details of its implementation and
      concentrate on how it interacts with other chunks."
- In Category Theory all you can ever really know is how entities connect to other entities through arrows

### Challenges

1. Implement, as good as you can, the identity function in your favorite language
   ```kotlin
   fun <A> identity(a: A): A = a
   
   val identity: (String) -> String = ::identity
   ```
2. Implement the composition function in your favorite language. It takes two functions as arguments and returns a
   function that is their composition.
   ```kotlin
   infix fun <A, B, C> ((B) -> C).compose(f: (A) -> B): (A) -> C = { this(f(it)) }
   
   val f: (Int) -> String = { it.toString() }
   val g: (String) -> Double = { it.toDouble() }
   val h: (Double) -> Float = { it.toFloat() }
   val g_after_f: (Int) -> Double = g compose f
   val h_after_g_after_f: (Int) -> Float = h compose g compose f
   ```
3. Write a program that tries to test that your composition function respects identity.
   ```kotlin
   val f1: (String) -> String = { it }
   val f2: (String) -> String = f1 compose identity
   val f3: (String) -> String = identity compose f1
   ```
4. Is the world-wide web a category in any sense? Are links morphisms?  
   There is some sense if you treat it just as "a path to". Refreshing could be the identity morphism. But, in another
   perspective, every page probably doesn't contain a link to itself. Links A -> B -> C does not necessarily mean a page
   has a link A -> C. Depends on how we define it.
5. Is Facebook a category, with people as objects and friendships as morphisms?  
   Since "friendship" is the morphism, then A friends with B friends with C does not imply A friends with C.
6. When is a directed graph a category?  
   Basically when it looks
   like [this](https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Category_SVG.svg/2560px-Category_SVG.svg.png).
   You need each node to have an edge back to itself and for each node it connects to, we also need connections to that
   node's connections.