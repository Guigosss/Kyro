const container = document.getElementById("exercises-container");
const addExerciseButton = document.getElementById("add-exercise");
const exerciseTemplate = document.getElementById("exercise-template");

let exerciseIndex = 0;
let setIndex = 0;

addExerciseButton.addEventListener("click", function () {

    exerciseIndex++;

    const exerciseBlock = document.createElement("div");
    exerciseBlock.classList.add("exercise-block");

    const header = document.createElement("div");
    header.classList.add("exercise-header");

    const exerciseLabel = document.createElement("label");
    exerciseLabel.textContent = "Exercice";

    const exerciseSelect = exerciseTemplate.cloneNode(true);
    exerciseSelect.removeAttribute("hidden");
    exerciseSelect.removeAttribute("id");
    exerciseSelect.name = `exercise-${exerciseIndex}`;

    const removeExerciseButton = document.createElement("button");
    removeExerciseButton.type = "button";
    removeExerciseButton.textContent = "X";

    removeExerciseButton.addEventListener("click", function () {
        exerciseBlock.remove();
    });

    header.appendChild(exerciseSelect);
    header.appendChild(removeExerciseButton);

    const setsContainer = document.createElement("div");
    setsContainer.classList.add("sets-container");

    const setsHeader = document.createElement("div");
    setsHeader.classList.add("sets-header");

    setsHeader.innerHTML = `
        <span>Série</span>
        <span>Poids</span>
        <span>Reps</span>
        <span></span>
    `;

    const addSetButton = document.createElement("button");
    addSetButton.type = "button";
    addSetButton.textContent = "+ Ajouter une série";

    addSetButton.addEventListener("click", function () {
        addSet(exerciseBlock, setsContainer, exerciseSelect);
    });

    exerciseBlock.appendChild(header);
    exerciseBlock.appendChild(setsHeader);
    exerciseBlock.appendChild(setsContainer);
    exerciseBlock.appendChild(addSetButton);

    container.appendChild(exerciseBlock);

    addSet(exerciseBlock, setsContainer, exerciseSelect);
});

function addSet(exerciseBlock, setsContainer, exerciseSelect) {

    const currentSetIndex = setIndex++;

    const setRow = document.createElement("div");
    setRow.classList.add("set-row");

    const setNumber = setsContainer.children.length + 1;

    setRow.innerHTML = `
        <span>${setNumber}</span>

        <input
            type="number"
            name="sets[${currentSetIndex}].weight"
            min="0"
            step="0.1"
            required
        >

        <input
            type="number"
            name="sets[${currentSetIndex}].reps"
            min="1"
            required
        >

        <input
            type="hidden"
            name="sets[${currentSetIndex}].exerciseId"
        >

        <button type="button" class="remove-set">
            X
        </button>
    `;

    const exerciseIdInput =
        setRow.querySelector(`input[name="sets[${currentSetIndex}].exerciseId"]`);

    exerciseIdInput.value = exerciseSelect.value;

    exerciseSelect.addEventListener("change", function () {
        const exerciseIdInputs =
            setsContainer.querySelectorAll('input[type="hidden"]');

        exerciseIdInputs.forEach(function (input) {
            input.value = exerciseSelect.value;
        });
    });

    const removeSetButton = setRow.querySelector(".remove-set");

    removeSetButton.addEventListener("click", function () {
        setRow.remove();
        updateSetNumbers(setsContainer);
    });

    setsContainer.appendChild(setRow);
}

function updateSetNumbers(setsContainer) {

    const rows = setsContainer.querySelectorAll(".set-row");

    rows.forEach(function (row, index) {
        row.querySelector("span").textContent = index + 1;
    });
}