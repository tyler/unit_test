Unit Test Framework for Clojure
===============================
<small>Written by Shane Celis on 2008-06-22</small>

<small>Cleanup and extensions, Tyler McMullen and Tom Faulhaber, Fall 2008</small>

This document describes a unit test framework for Clojure.  It is a
[xUnit framework][1] in the same vein as [JUnit][2].  

This version of unit-test is a "double fork." Tyler took Shane's original work
and brought it up-to-date with the latest Clojure versions and Tom
cleaned it up some and added some new features.

Download
--------

The "double-forked" versions is hosted at github
[here](http://github.com/tomfaulhaber/unit_test). There, you can download the
latest tarball, browse the source, and review change history.

Shane's original tarball is
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

Note: this has been modified somewhat in the latest version to ignore all
stacktrace elements before the "run-test" call. This is more generally reliable
than the ignore list. However, the ignore list is also still present.

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

Run all the tests from a specific namespace with a second argument to run-all-tests:

    user> (run-all-tests 'unit-test.examples.examples)
    ..E.E.F..
    Tests run 6 failures 1 errors 2
    Failures:
    1) failing-test: 
     Expected: 1 but was: 2 <- (+ 1 1) 
        unit_test.examples.examples$failing_test__4271.invoke(examples.clj:23)
    Errors:
    1) error-test: 
     java.lang.Exception: I am an exception in error-test.
        unit_test.examples.examples$error_test__4274.invoke(examples.clj:25)
    2) failing-catching-test: 
     java.lang.Exception: I am an error in failing-catching-test.
        unit_test.examples.examples$failing_catching_test__4283$fn__4285.invoke(examples.clj:35)
        unit_test.examples.examples$failing_catching_test__4283.invoke(examples.clj:35)
    nil

To run a subset of the tests in a namespace, add a
regular expression as the third argument:

    user> (run-all-tests 'unit-test.examples.examples #"-catching-")
    .E
    Tests run 1 failures 0 errors 1
    Errors:
    1) failing-catching-test: 
     java.lang.Exception: I am an error in failing-catching-test.
        unit_test.examples.examples$failing_catching_test__4283$fn__4285.invoke(examples.clj:35)
        unit_test.examples.examples$failing_catching_test__4283.invoke(examples.clj:35)
    nil

The simple-test macro can be used to create sequences of simple tests. This
is useful when you want to run a large number of variations and check the
equality of the results:

    (simple-tests cltl-B-tests
      (format nil "~,,' ,4B" 0xFACE) "1111 1010 1100 1110" 
      (format nil "~,,' ,4B" 0x1CE) "1 1100 1110" 
      (format nil "~19,,' ,4B" 0xFACE) "1111 1010 1100 1110" 
      (format nil "~19,,' ,4B" 0x1CE) "0000 0001 1100 1110")

The tests will be named with the name specified with sequential integers
appended (cltl-B-tests-1, cltl-B-tests-2, etc.).

Conclusion
----------

I hope that is enough to allow one to pick up this unit-test framework
for Clojure and write some unit tests.  This introduction was very
quick, so please see the links listed above for a more thorough
introduction to unit testing.  Look at `explore-clojure.clj` for some
examples of unit tests.

[1]: http://en.wikipedia.org/wiki/XUnit
[2]: http://www.junit.org/
