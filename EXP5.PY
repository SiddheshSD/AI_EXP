import numpy as np
def find_neighbours(state, landscape):
    neighbours = []
    dim = landscape.shape
    x, y = state
    
    if x != 0:
        neighbours.append((x - 1, y))
    if x != dim[0] - 1:
        neighbours.append((x + 1, y))
    if y != 0:
        neighbours.append((x, y - 1))
    if y != dim[1] - 1:
        neighbours.append((x, y + 1))
    if x != 0 and y != 0:
        neighbours.append((x - 1, y - 1))
    if x != 0 and y != dim[1] - 1:
        neighbours.append((x - 1, y + 1))
    if x != dim[0] - 1 and y != 0:
        neighbours.append((x + 1, y - 1))
    if x != dim[0] - 1 and y != dim[1] - 1:
        neighbours.append((x + 1, y + 1))
    return neighbours

def hill_climb(curr_state, landscape):
    neighbours = find_neighbours(curr_state, landscape)
    ascended = False
    next_state = curr_state
    for neighbour in neighbours:
        if landscape[neighbour[0]][neighbour[1]] > landscape[next_state[0]][next_state[1]]:
            next_state = neighbour
            ascended = True
    return ascended, next_state

def main():
    landscape = np.random.randint(1, 50, size=(10, 10))
    print(landscape)
    start_state = (8, 8)
    current_state = start_state
    count = 1
    ascending = True
    while ascending:
        print("\nStep #", count)
        print("Current state coordinates:", current_state)
        print("Current state value:", landscape[current_state[0]][current_state[1]])
        count += 1
        ascending, current_state = hill_climb(current_state, landscape)
    print("\nStep #", count)
    print("Optimization objective reached.")
    print("Final state coordinates:", current_state)
    print("Final state value:", landscape[current_state[0]][current_state[1]])

main()