(ns maze-project.interface.ui-cell
  (:use [seesaw.core]
      [seesaw.graphics]
      [seesaw.font]
      [seesaw.color]
      [seesaw.border]))


(defn draw-cell [cell g xPos yPos width height]
  (let [verticalWallThickness (int (Math/floor (/ width  10)))
        horizontalWallThickness (int (Math/floor (/ height 10)))
        wallStyle (style :background (color "#00d6b9"))]
    (if (= (:north cell) 0) (draw g (rect xPos yPos width horizontalWallThickness) wallStyle))
    (if (= (:east cell) 0) (draw g (rect (- (+ xPos width) verticalWallThickness) yPos verticalWallThickness height) wallStyle))
    (if (= (:south cell) 0) (draw g (rect xPos (- (+ yPos height) horizontalWallThickness) width horizontalWallThickness) wallStyle))
    (if (= (:west cell) 0) (draw g (rect xPos yPos verticalWallThickness height) wallStyle))))