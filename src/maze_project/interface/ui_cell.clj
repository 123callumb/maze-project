(ns maze-project.interface.ui-cell
  (:use [seesaw.core]
      [seesaw.graphics]
      [seesaw.font]
      [seesaw.color]
      [seesaw.border]))


(defn draw-cell [cell g xPos yPos width height]
  (let [verticalWallThickness (int (Math/floor (/ width  10)))
        horizontalWallThickness (int (Math/floor (/ height 10)))
        adjWidth (+ verticalWallThickness width)
        adjHeight (+ horizontalWallThickness height)
        wallStyle (style :background (color 40 40 40))]
    (if (= (:north cell) 0) (draw g (rect xPos yPos adjWidth horizontalWallThickness) wallStyle))
    (if (= (:east cell) 0) (draw g (rect (- (+ xPos adjWidth) verticalWallThickness) yPos verticalWallThickness adjHeight) wallStyle))
    (if (= (:south cell) 0) (draw g (rect xPos (- (+ yPos adjHeight) horizontalWallThickness) adjWidth horizontalWallThickness) wallStyle))
    (if (= (:west cell) 0) (draw g (rect xPos yPos verticalWallThickness adjHeight) wallStyle))))