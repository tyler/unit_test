;; unit-test-examples.clj
;;
;; Copyright (c) Shane Celis. 
;;
;; The use and distribution terms for this software are covered by the
;; Common Public License 1.0 (http://opensource.org/licenses/cpl.php)
;; which can be found in the file CPL.TXT at the root of this
;; distribution.  By using this software in any fashion, you are
;; agreeing to be bound by the terms of this license.  You must not
;; remove this notice, or any other, from this software.

;; This test demonstrates a number of simple tests, and also exercises
;; the UI in terms of what it shows for failures and errors.

(ns unit-test.examples.examples
    (:use unit-test))


(deftest good-test []
  (assert-equal 1 1))

(deftest failing-test []
  (assert-equal 1 (+ 1 1)))

(deftest error-test []
  (throw (new Exception "I am an exception in error-test.")))

(deftest catching-test []
  (assert-expect Exception
                 (do 
                   (throw (new Exception "I am an error in catching.")))
                 "Didn't get an exception"))

(deftest failing-catching-test []
  (assert-expect java.lang.AssertionError
                 (do 
                   (throw (new Exception "I am an error in failing-catching-test.")))
                 "Didn't get an exception"))

(deftest test-assertions []
  (let [x 1
        y 2
        z 1
        a "A"
        b "B"
        c "A"]
    (assert-equal x z)
    (assert-not-equal 1 2)
    (assert-same x x)
    (assert-same x z) ;; <-- that's a little bit of a surprise.
    (assert-same "7" "7")
    (assert-not-same "7" (str 7))
    (assert-same c a)
    (assert-nil nil)
    (assert-not-nil x)
    (assert-true true)
    (assert-true (= 1 1))
    (assert-true "hi")
    (assert-not-true false)
    ))


