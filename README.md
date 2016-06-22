# wines

practicing clojure with seesaw / http-kit

Running fast:

project-dir> lein trampoline repl :headless
project-dir> grench main wines.core

-------------
(clojure.pprint/cl-format nil "~r" 24343)

(pst)
(instance?)
(type {:x 1})
(.printStackTrace *e)

(keys (ns-publics 'namespace))

*ns* ; current namespace

*print-length*
;dynamic var
;*print-length* controls how many items of each collection the
;printer will print.

(print (apply str (interleave (all-ns) (repeat "\n")))) ; println all available namespaces

((comp inc *) 2 3)
; => 7
loop
recur instead of calling the function by name... optimized

memoize

time

(def ^:dynamic x 1)
user=> (def ^:dynamic y 1)
user=> (+ x y)
2

user=> (binding [x 2 y 3]
         (+ x y))

(Thread/sleep 1000)

get, assoc, get-in assoc-in

if-let (shorthand)

name / keyword

first, rest, last, nth

count: returns number of items in collection

reverse -> non lazy returning reversed seq items from collection

(ns-interns 'wines.cli) ; returns internal def's vars. ns-publics only the public ones.

(in-ns 'wines.http) ; switches to that namespace

(System/getProperty "user.dir")

## Installation

TODO

## Usage

FIXME: explanation

$ java -jar wines-0.1.0-standalone.jar

## Options

-h
-s

## Examples

...

### Bugs

...

## License

Copyright © 2016 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
