import sys

context = []

for ln in sys.stdin:
  if ln[0:4] == 'CALL':
    context.append(ln[5:-1])
    print context
  else:
    context = context[:-1]

  

