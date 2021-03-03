(ns maze-project.interfaces.ui-cell
  (:use [seesaw.core]
      [seesaw.graphics]
      [seesaw.font]
      [seesaw.color]
      [seesaw.border]))


(defn draw-cell [cell g xPos yPos size]
  (draw g (rect xPos yPos size size) (style :background (color 40 40 40)))
  (let [wallThickness (int (Math/floor (/ size 10)))]
    (if (= (:north cell) 0) (draw g (rect xPos yPos size wallThickness) (style :background (color 13 224 143))))
    (if (= (:east cell) 0) (draw g (rect (- (+ xPos size) wallThickness) yPos wallThickness size) (style :background (color 13 234 43))))
    (if (= (:south cell) 0) (draw g (rect xPos (- (+ yPos size) wallThickness) size wallThickness) (style :background (color 13 244 83))))
    (if (= (:west cell) 0) (draw g (rect xPos xPos wallThickness size) (style :background (color 13 254 243))))))