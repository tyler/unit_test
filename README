Unit Test Framework for Clojure
===============================
<small>Written by Shane Celis on 2008-06-22</small>

This document describes a unit test framework for Clojure.  It is a
[xUnit framework][1] in the same vein as [JUnit][2].  

Download
--------

Here is the tarball
[unit-test_v0.1.0.tgz](http://gnufoo.org/clojure/unit-test_v0.1.0.tgz).

Examples
--------

I think it is best illustrated with some simple code examples.

     (in-ns 'user)
     (refer 'unit-test)

     (deftest my-example-test []
       (let [x 1]
         (assert-equal 1 x "x is NOT one!")))

One can then run the tests like this:

    user> (run-all-tests)
    .
    Tests run 1 failures 0 errors 0
    nil

The output is very terse.  This is typical for passing unit tests.
One does not want to print out anything unless something fails.  Let
us create a new test that will fail to show what its output will look
like.

     (deftest my-failing-example-test []
       (let [x 1]
         (assert-equal 1 (inc x) "is NOT one!")))

Running it produces this output:

    user> (run-all-tests)
    ..F
    Tests run 2 failures 1 errors 0
    Failures:
    1) Expected: 1 but was: 2 <- (inc x) is NOT one!
         clojure.fns.user.my_failing_example_test__2889.invoke(readme-examples.clj:45)
    nil

The failure message shows what value was expected, what value was
produced, and thanks to macros what the expression was that produced
that value and the message.

The failure also produces a stack trace.  The stack trace shown has
been filtered of all Java files, code from `boot.clj` and
`unit-test.clj`, so that it hopefully identifies only the code that is
pertinent to the failing test.  The filters on the stack trace can be
modified by changing the `ignore-files` definition.

Finally, let me demonstrate a test that produces an error, i.e. an
exception.

    (deftest my-error-example-test []
      (let [x 1]
        (cast String x)))

Its output:

    user> (run-all-tests)
    ..E.F
    Tests run 3 failures 1 errors 1
    Failures:
    1) Expected: 1 but was: 2 <- (inc x) is NOT one!
        clojure.fns.user.my_failing_example_test__2900.invoke(readme-examples.clj:45)
    Errors:
    1) java.lang.ClassCastException
        clojure.fns.user.my_error_example_test__2903.invoke(readme-examples.clj:27)
    nil

Conclusion
----------

I hope that is enough to allow one to pick up this unit-test framework
for Clojure and write some unit tests.  This introduction was very
quick, so please see the links listed above for a more thorough
introduction to unit testing.  Look at `explore-clojure.clj` for some
examples of unit tests.

[1]: http://en.wikipedia.org/wiki/XUnit
[2]: http://www.junit.org/
