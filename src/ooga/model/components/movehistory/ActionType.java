package ooga.model.components.movehistory;

/**
 * Different types of piece movements. Used for keeping track of movement history in order to undo
 * moves
 *
 * @author Casey Szilagyi
 */
public enum ActionType {
  MOVE,
  REMOVE,
  ADD
}
