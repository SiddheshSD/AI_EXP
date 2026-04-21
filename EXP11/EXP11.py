# Simple Optimizing Compiler Example
def constant_folding(expr):
    try:
        return str(eval(expr))
    except:
        return expr
def optimize_code(code):
    optimized = []
    seen = {}
    for line in code:
        if "=" in line:
            var, expr = line.split("=")
            expr = expr.strip()
            # Constant folding
            expr = constant_folding(expr)
            # Common subexpression elimination
            if expr in seen:
                optimized.append(f"{var.strip()} = {seen[expr]}")
            else:
                seen[expr] = var.strip()
                optimized.append(f"{var.strip()} = {expr}")
        else:
            optimized.append(line)
    return optimized

code = [
    "t1 = 5 + 3",
    "t2 = 5 + 3",
    "x = t1 + t2"
]

result = optimize_code(code)
print("Optimized Code:")
for line in result:
    print(line)