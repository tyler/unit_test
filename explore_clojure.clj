;; explore-clojure.clj
;;
;; Copyright (c) Shane Celis. 
;;
;; The use and distribution terms for this software are covered by the
;; Common Public License 1.0 (http://opensource.org/licenses/cpl.php)
;; which can be found in the file CPL.TXT at the root of this
;; distribution.  By using this software in any fashion, you are
;; agreeing to be bound by the terms of this license.  You must not
;; remove this notice, or any other, from this software.

;; This is just some exploratory programming of clojure encapsulated
;; as tests.

(use 'unit-test)

(deftest test-shadow-fns []
  (assert-true (= 3 (+ 1 2)))
  (let [+ (fn [& xs] 4)]
    ;; Cool. I can shadow functions.  Expected for a Lisp-1, but I like
    ;; seeing it for myself.
    (assert-true (= 4 (+ 1 2)))))

(deftest test-assert-equal []
  (assert-equal 3 (+ 1 2))
  (assert-fail
   (assert-equal 4 (+ 1 2) "we expect this failure"))
  (assert-fail
   (assert-equal 4 (+ 1 2))
   ))

(deftest test-split-with []
  "split-with doesn't work the way I'd expect"
  (let [[string other] (split-with string? [1 "2" 3 "4"])]
    (assert-equal nil string)
    (assert-equal [1 "2" 3 "4"] other)))

(deftest test-maps []
  (let [h {:a 1 :b 2 :c 3}]
    (assert-equal 1 (h :a))
    (assert-equal 1 (:a h))
    (assert-equal {:a 1 :b 2 :c 4} (assoc h :c 4))
    ))

(deftest test-apply-map-macros 
;; XXX - deftest doesn't work with string documents.  FIX ME.
;;     "This is interesting, one can apply and map macros, which 
;;     both provide strange results, but one can then evaluate those
;;     results and get the right result.  I'm not sure if it was 
;;     intentional, but I think it's an interesting 'feature'."  
  []
  (assert-true (not= false (apply and '(true false))))
  (assert-true (= false (eval (apply and '(true false)))))

  (assert-true (not= true (apply and '(true true))))
  (assert-true (= true (eval (apply and '(true true)))))

  ;; You can even map the macros, but then you have to map the evaluations.
  (assert-true (not= '(true false) (map and '(true false) '(true true))))
  (assert-true (= '(true false) (map eval (map and '(true false) '(true true)))))
  )

(deftest test-reduce []
  (let [a [1 2 3]
        f (fn [s n] (str s n))
        g (fn [n s] (str s n))
        ]
    (assert-equal 6 (reduce + a))
    (assert-equal 6 (reduce + 0 a))
    (assert-equal 7 (reduce + 1 a))
    (assert-equal "7" (str 7))
    (assert-equal "begin123" (reduce f "begin" a))
    (assert-equal "321begin" (reduce g "begin" a))
    ))

(deftest test-local-vars []
  (let [f (fn [y] (var-set y (inc @y)))]
    (with-local-vars [x 1]
      (assert-equal 1 @x)
                                        ;    (set! '@x 2)
      (var-set x 2)
      (assert-equal 2 @x)
      (f x)
      (assert-equal 3 @x)
    )))

(defn collector []
  (with-local-vars [l '()]
    (fn [x]
      (if (= x :list)
        (var-get l)
        (var-set l (cons x @l))))))

(deftest test-collector []
  ;; Nope.  Doesn't work the way I expected.
  (assert-expect Exception
                 (let [f (collector)]
                   (assert-equal '() (f :list)))))


