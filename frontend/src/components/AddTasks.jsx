import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faPlus, faX } from "@fortawesome/free-solid-svg-icons";
import { motion, AnimatePresence } from "framer-motion";
import { useState } from "react";
import axios from "axios";

export default function AddTask() {
  const [taskName, setTaskName] = useState("");
  const [desc, setDescName] = useState("");
  const [dueDate, setDueDate] = useState("");
  const [status, setStatus] = useState("");

  const [isSpinning, setIsSpinning] = useState(false);
  const [isOpen, setIsOpen] = useState(false);

  const variants = {
    rotate: { rotate: [0, 0, 270, 270, 0], transition: { duration: 1.4 } },
    stillRotate: { rotate: [0, 0, 270, 270, 0], transition: { duration: 1.4 } },
  };

  const addTasks = async (e) => {
    e.preventDefault();
    try {
      const token = localStorage.getItem("JWT");
      const response = await axios.post(
        "http://localhost:8080/api/tasks",
        {
          title: taskName,
          desc: desc,
          dueDate: dueDate,
          status: status,
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      if (response.status == 200) {
        console.log("Task added");
      }
    } catch (e) {
      console.log("Task failed to be added to the system:", e.message);
    }
  };

  function toggleSpin() {
    setIsSpinning(!isSpinning);
    toggleModalFunction();
  }

  function toggleModalFunction() {
    setIsOpen(!isOpen);
  }

  return (
    <>
      <div className="h-1/6 flex items-end justify-center">
          <div className="footer items-center p-4 rounded-lg text-neutral-content w-1/2 drop-shadow-lg bg-base-200 mb-6">
            <aside>
              <motion.a
                onClick={toggleSpin}
                className="items-center grid-flow-col flex flex-row cursor-pointer"
                whileHover={{
                  scale: 1.025,
                  transition: { duration: 0.1 },
                }}
                whileTap={{ scale: 0.9 }}
              >
                <motion.div
                  initial={false}
                  animate={isSpinning ? "rotate" : "stillRotate"}
                  variants={variants}
                >
                  <FontAwesomeIcon icon={faPlus} />
                </motion.div>
                <div className="ml-2">Add task</div>
              </motion.a>
            </aside>
          </div>
          <AnimatePresence>
            {isOpen && (
              <motion.form
                onSubmit={addTasks}
                className="card-body w-1/4 h-3/5 absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 bg-primary-content rounded-lg flex justify-center align-middle"
                initial={{ opacity: 0 }}
                animate={{ opacity: 1 }}
                exit={{ opacity: 0 }}
              >
                <div className="p-4">
                  <motion.div
                    className="fixed top-5 right-5"
                    whileHover={{ scale: 1.085 }}
                    whileTap={{ scale: 0.9 }}
                  >
                    <FontAwesomeIcon
                      className="cursor-pointer"
                      size="xl"
                      icon={faX}
                      onClick={toggleModalFunction}
                    />{" "}
                  </motion.div>
                  <div className="form-control">
                    <h2 className="text-2xl font-bold text-center pb-3">
                      Enter task details below
                    </h2>
                    <label className="label">
                      <span className="label-text">Task Name</span>
                    </label>
                    <input
                      type="text"
                      placeholder="task name"
                      onChange={(e) => setTaskName(e.target.value)}
                      className="input input-bordered"
                      required
                    />
                  </div>
                  <div className="form-control">
                    <label className="label">
                      <span className="label-text">Description</span>
                    </label>
                    <input
                      type="text"
                      placeholder="description"
                      onChange={(e) => setDescName(e.target.value)}
                      className="input input-bordered"
                    />
                  </div>
                  <div className="form-control">
                    <label className="label">
                      <span className="label-text">Due Date</span>
                    </label>
                    <input
                      type="text"
                      placeholder="due date"
                      onChange={(e) => setDueDate(e.target.value)}
                      className="input input-bordered"
                    />
                  </div>
                  <div className="form-control">
                    <label className="label">
                      <span className="label-text">Status</span>
                    </label>
                    <input
                      type="text"
                      placeholder="status"
                      onChange={(e) => setStatus(e.target.value)}
                      className="input input-bordered"
                    />
                  </div>
                  <div className="form-control mt-6">
                    <button className="btn btn-primary">Submit task</button>
                  </div>
                </div>
              </motion.form>
            )}
          </AnimatePresence>
        </div>
    </>
  );
}
