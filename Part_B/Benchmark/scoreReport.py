import os
import sys
import pickle
import math
from decimal import Decimal

# which = 'method' or 'stack'
which=sys.argv[1]

if not (which == 'method' or which == 'stack'):
  sys.stderr.write("usage: scoreReport.py [method|stack] passFail.csv test1.cct test2.cct ...\n")
  exit(1)

counts      = {}
passed      = {}
passFailCsv = sys.argv[2]
testNames   = []
testFiles   = sys.argv[3:]

# load pass/fail
for ln in open(passFailCsv):
  [testNm,status] = ln.split(',')
  testNames.insert(0,testNm)
  if(status == "PASS\n"):
      passed[testNm] = True
  else:
      passed[testNm] = False
      
def setCount(stack, test, count):
  if not counts.has_key(stack):
      counts[stack] = {}

  if not counts[stack].has_key(test):
      counts[stack][test] = 0

  counts[stack][test] += count

# load counts
for f in testFiles:
  testName = os.path.basename(f)[:-4]
  d = pickle.load(open(f))
  for (stack,count) in d.iteritems():
    if which=='method':
        # method-level
        setCount(stack.split(':')[-1], testName, count)
    else:
        # stack-level
        setCount(stack, testName, count)

passing_tests = [t for t in passed if passed[t]]
failing_tests = [t for t in passed if not passed[t]]
num_passing_tests = Decimal(len(passing_tests))
num_failing_tests = Decimal(len(failing_tests))

# calculate methods called in each tests
methods_called_per_test = {}
for t in testNames:
  methods_called_per_test[t] = set(
   [nm for nm in counts if counts[nm].has_key(t)]
  )

# calculate number of calls in each test
calls_per_test = {}
for t in testNames:
  calls_per_test[t] = Decimal(sum(
    [counts[nm].get(t,0) for nm in counts]
  ))

calls_in_passing_tests = Decimal(sum([calls_per_test[t] for t in passing_tests]))
calls_in_failing_tests = Decimal(sum([calls_per_test[t] for t in failing_tests]))

passing_tests_per_method = {}
failing_tests_per_method = {}
for nm in counts:
  passing_tests_per_method[nm] = Decimal(len(
    [1 for t in counts[nm] if passed[t] and counts[nm][t] > 0]
  ))
  failing_tests_per_method[nm] = Decimal(len(
    [1 for t in counts[nm] if not passed[t] and counts[nm][t] > 0]
  ))

calls_in_passing_tests_per_method = {}
for nm in counts:
  calls_in_passing_tests_per_method[nm] = Decimal(sum(
    [counts[nm].get(t,0) for t in passing_tests]
  ))

calls_in_failing_tests_per_method = {}
for nm in counts:
  calls_in_failing_tests_per_method[nm] = Decimal(sum(
    [counts[nm].get(t,0) for t in failing_tests]
  ))

def tarantula(stack):
  pctPassed = passing_tests_per_method[stack] / num_passing_tests
  pctFailed = failing_tests_per_method[stack] / num_failing_tests
  return pctFailed / (pctPassed + pctFailed)

def tarantula_weighted(stack):
  pctPassed = calls_in_passing_tests_per_method[stack] / calls_in_passing_tests
  pctFailed = calls_in_failing_tests_per_method[stack] / calls_in_failing_tests
  return pctFailed / (pctPassed + pctFailed)

def sbi(stack):
  passed = passing_tests_per_method[stack]
  failed = failing_tests_per_method[stack]
  return failed / (passed + failed)

def sbi_weighted(stack):
  passed = calls_in_passing_tests_per_method[stack]
  failed = calls_in_failing_tests_per_method[stack]
  return failed / (passed + failed)

def jaccard(stack):
  passed = passing_tests_per_method[stack]
  failed = failing_tests_per_method[stack]
  return failed / (passed + num_failing_tests)

def jaccard_weighted(stack):
  passed = calls_in_passing_tests_per_method[stack]
  failed = calls_in_failing_tests_per_method[stack]
  return failed / (passed + calls_in_failing_tests)

def ochiai(stack):
  passed = passing_tests_per_method[stack]
  failed = failing_tests_per_method[stack]
  return failed / Decimal(math.sqrt(num_failing_tests * (passed + failed)))
  
def ochiai_weighted(stack):
  passed = calls_in_passing_tests_per_method[stack]
  failed = calls_in_failing_tests_per_method[stack]
  return failed / Decimal(math.sqrt(calls_in_failing_tests * (passed + failed)))
  
def score(f):
  # wrap score function to catch divide-by-zero exceptions
  def go(stack):
      try:
          return f(stack)
      except:
          return 0
  return go

# compute scores
scoreFunctions = {
  'tarantula'          : score(tarantula),
  'tarantula_weighted' : score(tarantula_weighted),
  'sbi'                : score(sbi),
  'sbi_weighted'       : score(sbi_weighted),
  'jaccard'            : score(jaccard),
  'jaccard_weighted'   : score(jaccard_weighted),
  'ochiai'             : score(ochiai),
  'ochiai_weighted'    : score(ochiai_weighted)
}

scoreNms = sorted(scoreFunctions.keys())

for stack in counts.iterkeys():
  print ','.join([stack] + [str(f(stack)) for f in scoreFunctions.values()])


