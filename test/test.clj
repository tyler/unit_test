;; unit-test-test.clj
;;
;; Copyright (c) Shane Celis. 
;;
;; The use and distribution terms for this software are covered by the
;; Common Public License 1.0 (http://opensource.org/licenses/cpl.php)
;; which can be found in the file CPL.TXT at the root of this
;; distribution.  By using this software in any fashion, you are
;; agreeing to be bound by the terms of this license.  You must not
;; remove this notice, or any other, from this software.

;; All right.  Here's where we go meta.  This tests the unit-test
;; framework.  It's a nice demonstration of the framework being
;; flexible enough to test itself.

(ns unit-test.test
    (:use unit-test)
    (:use unit-test.examples.examples))

(defn result-count [test-result]
  ;; There's a map map waiting to bust out from this code.
  {:tests (count (:tests test-result))
   :failures (count (:failures test-result))
   :errors (count (:errors test-result))})

(defn run [& tests]
  (result-count (exec-tests tests)))

(defn assert-result [t f e r & msgs]
  (assert-equal t (:tests r) (str msgs))
  (assert-equal f (:failures r) (str msgs))
  (assert-equal e (:errors r) (str msgs))
  )

(deftest test-good-test []
  (let [r (run #'good-test)]
    (assert-result 1 0 0 r)))

(deftest test-failing-test []
  (let [r (run #'failing-test)]
    (assert-result 1 1 0 r)))

(deftest test-error-test []
  (let [r (run #'error-test)]
    (assert-result 1 0 1 r)))

(deftest test-catching-test []
  (let [r (run #'catching-test)]
    (assert-result 1 0 0 r)))

(deftest test-failing-catching-test []
  (let [r (run #'failing-catching-test)]
    (assert-result 1 0 1 r)))

(deftest test-all-combo []
  (let [r (run #'good-test #'failing-test #'error-test #'catching-test #'failing-catching-test)]
    (assert-result 5 1 2 r)))

(deftest test-failing-assertions []
  (let [x 1
        y 2
        z 1]
    (assert-fail-each
     ;; These assertions are all failing assertions--that are caught
     (assert-equal x y)
     (assert-equal 2 1)
     (assert-same x y)
     (assert-not-same x x)
     (assert-not-same x z) ;; <-- that's a little bit of a surprise.
     (assert-not-same "7" "7")
     (assert-same "7" (str 7))
     (assert-nil x)
     (assert-not-nil nil)
     (assert-true false)
     (assert-true (= 1 2))
     (assert-false "hi")
     (assert-not-true true)
    )))
