import collections

graph1 = {
    0 : [1,2,3],
    1 : [2],
    2 : [4],
    3 : [],
    4 : [],
}
graph = {
    5 : [3,7],
    3 : [2,4],
    7 : [8],
    2 : [],
    4 : [8],
    8 : []
}

def bfs(graph, node):
    visited = []
    queue = collections.deque([node])

    while queue :
        vertex = queue.popleft()
        visited.append(vertex)

        for neighbour in graph [vertex]:
            if neighbour not in visited:
                queue.append(neighbour)
        
        print (visited)

print("Follwing is the breadth-first search")
bfs(graph,5)