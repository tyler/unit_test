;; readme-examples.clj
;;
;; Copyright (c) Shane Celis. 
;;
;; The use and distribution terms for this software are covered by the
;; Common Public License 1.0 (http://opensource.org/licenses/cpl.php)
;; which can be found in the file CPL.TXT at the root of this
;; distribution.  By using this software in any fashion, you are
;; agreeing to be bound by the terms of this license.  You must not
;; remove this notice, or any other, from this software.

;; This is code that is demonstrated in the README file.

(use 'unit-test)

(deftest my-example-test []
  (let [x 1]
    (assert-equal 1 x "x is NOT one!")))

(deftest my-failing-example-test []
  (let [x 1]
    (assert-equal 1 (inc x) "(inc x) is NOT one!")))

(deftest my-error-example-test []
  (let [x 1]
    (cast String x)))
