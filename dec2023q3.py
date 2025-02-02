def get_hit_dict(cmd, start_pos, targets):
    assert isinstance(targets, set)
    d = {}
    pos = start_pos
    for i in range(len(cmd)):
        c = cmd[i]
        if c == "L":
            pos -= 1
        elif c == "R":
            pos += 1
        else:
            if pos in targets:
                d[pos] = d[pos] + 1 if pos in d else 1
    return d


def f(targets, cmd):
    targets = set(targets)
    offsets = [-2, -1, 0, 1, 2]

    hit_dicts = {}
    for offset in offsets:
        hit_dict = get_hit_dict(cmd, offset, targets)
        hit_dicts[offset] = hit_dict

    prev_pos = None
    pos = 0
    max_score = -1
    for i in range(len(cmd)):
        c = cmd[i]
        prev_c = cmd[i - 1] if i >= 1 else None

        if c == "L":
            pos -= 1
        elif c == "R":
            pos += 1

        # calc for all offsets
        for offset in offsets:
            if offset == 0: 
                continue
            hit_dict = hit_dicts[offset]
            offset_pos = pos + offset
            if c == "F" and offset_pos in targets:
                # remove at index i
                hit_dict[offset_pos] -= 1
                if hit_dict[offset_pos] == 0:
                    del hit_dict[offset_pos]
            if prev_c == "F" and i >= 1 and prev_pos in targets:
                # add at index i - 1
                hit_dict[prev_pos] = hit_dict[prev_pos] + 1 if prev_pos in hit_dict else 1
            # print(offset, dict(hit_dict), len(hit_dict))
                
        # detemine which offsets reachable when changing move i
        for new_c in "LRF":
            if c == "L" and new_c == "R":
                cand_score = len(hit_dicts[2])
            elif c == "R" and new_c == "L":
                cand_score = len(hit_dicts[-2])
            elif c == "R" and new_c == "F":
                cand_score = len(hit_dicts[-1])
                if pos - 1 not in hit_dicts[-1]:
                    cand_score += 1
            elif c == "L" and new_c == "F":
                cand_score = len(hit_dicts[1])
                if pos + 1 not in hit_dicts[1]:
                    cand_score += 1
            elif c == "F" and new_c == "L":
                cand_score = len(hit_dicts[-1])
            elif c == "F" and new_c == "R":
                cand_score = len(hit_dicts[1])
            else:
                cand_score = len(hit_dicts[0])
            max_score = max(max_score, cand_score)
        prev_pos = pos
    
    print(max_score)

t, c = input().split()
targets = input().split()
targets = list(map(int, targets))
command = input()
f(targets, command)