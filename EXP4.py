import collections 

graph = {
    5 : [3,7],
    3 : [2, 4],
    7 : [8],
    2 : [],
    4 : [8],
    8 : []
}

start = 5
dest = 2
visited = []
stack = []
path = []

stack.append(start)

while stack:
    vertex = stack.pop()
    visited.append(vertex)
    if vertex == dest:
        print("Goal FOUND:", vertex)
        print(visited)
        break
    for neighbour in graph[vertex]:
        if neighbour not in visited:
            stack.append(neighbour)

            # if neighbour == dest :
            #     print("FOUND:", neighbour)
            #     print(visited)
            #     break
            # print(path)

