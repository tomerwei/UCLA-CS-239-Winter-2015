import os
import sys
import pickle

counts      = {}
testFiles   = sys.argv[1:]
testNames   = []

def setCount(stack, test, count):
  if not counts.has_key(stack):
      counts[stack] = {}

  counts[stack][test] = count

for f in testFiles:
  testName = os.path.basename(f)[:-4]
  testNames.insert(0,testName)
  d = pickle.load(open(f))
  for (stack,count) in d.iteritems():
    setCount(stack, testName, count)

testNames.sort()

print ",".join(['Stack'] + testNames)
for (stack,testCounts) in counts.iteritems():
  row = [str(testCounts.get(testName,0)) for testName in testNames]
  row.insert(0,stack)
  print ",".join(row)

  
