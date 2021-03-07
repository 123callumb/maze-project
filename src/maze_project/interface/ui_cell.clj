(ns maze-project.interface.ui-cell
  (:use [seesaw.core]
      [seesaw.graphics]
      [seesaw.font]
      [seesaw.color]
      [seesaw.border]))


(defn draw-cell [cell g xPos yPos width height]
  (draw g (rect xPos yPos width height) (style :background (color 40 40 40)))
  (let [verticalWallThickness (int (Math/floor (/ width  10)))
        horizontalWallThickness (int (Math/floor (/ height 10)))]
    (if (= (:north cell) 0) (draw g (rect xPos yPos width horizontalWallThickness) (style :background (color 13 224 143))))
    (if (= (:east cell) 0) (draw g (rect (- (+ xPos width) verticalWallThickness) yPos verticalWallThickness height) (style :background (color 13 234 43))))
    (if (= (:south cell) 0) (draw g (rect xPos (- (+ yPos height) horizontalWallThickness) width horizontalWallThickness) (style :background (color 13 244 83))))
    (if (= (:west cell) 0) (draw g (rect xPos yPos verticalWallThickness height) (style :background (color 13 254 243))))))